package com.raion.furnitale.ui.cart

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.raion.furnitale.R
import com.raion.furnitale.core.domain.model.Product
import com.raion.furnitale.core.ui.CartAdapter
import com.raion.furnitale.core.ui.CartCheckoutAdapter
import com.raion.furnitale.databinding.CartFragmentBinding
import com.raion.furnitale.ui.MainActivity
import com.raion.furnitale.utils.Formatting
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    companion object {
        private const val ORDER_SUCCESS = 101
    }

    private val cartBinding by viewBinding<CartFragmentBinding>()
    private val cartViewModel: CartViewModel by viewModel()
    private val cartAdapter: CartAdapter by inject()
    private val cartCheckoutAdapter: CartCheckoutAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val account = GoogleSignIn.getLastSignedInAccount(context)

        cartBinding.rvCart.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }

        cartBinding.includeCartCheckout.rvAllCheckout.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartCheckoutAdapter
        }

        observeCart(account)
    }

    private fun observeCart(account: GoogleSignInAccount?) {
        cartViewModel.getCartList(account?.email).observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()) {
                cartBinding.includeEmptyCart.apply {
                    ivAddCart.visibility = View.VISIBLE
                    tvAddCart.visibility = View.VISIBLE
                }
            } else {
                cartBinding.includeEmptyCart.apply {
                    ivAddCart.visibility = View.INVISIBLE
                    tvAddCart.visibility = View.INVISIBLE
                }
            }
            cartCheckoutAdapter.setData(it)
            cartAdapter.settAllData(it)
            totalPrice(it)
            if (!it.isNullOrEmpty()) {
                checkout()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun totalPrice(listProduct: List<Product>) {
        var totalPrice = 0
        for (product in listProduct) {
            totalPrice += (product.totalStuffs?.let { product.realPrice?.times(it) }!!)
        }
        cartBinding.includeCartCheckout.tvPaymentTotalReal.text = "Rp ${Formatting.rupiahCurrencyFormatting(totalPrice)}"
    }
    
    private fun checkout() {
        cartBinding.includeCartCheckout.buttonCheckout.setOnClickListener {
            val checkoutDialog = SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE)
            checkoutDialog.apply {
                titleText = "Checkout Products?"
                confirmText = "YES"
                cancelText = "NO"
                setConfirmClickListener {
                    cartViewModel.deleteAllProduct()
                    it.titleText = "Checkout Success"
                    it.confirmText = "OK"
                    it.cancelText = null
                    it.setConfirmClickListener(null)
                    it.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                    buildNotification()
                }
                setCancelClickListener {
                    it.cancel()
                }
                show()
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun buildNotification() {
        val channelId = "order_success_channel"
        val channelName = "order_success_furnitale"

        val orderId = (1000000..9999999).random()
        val title = "Order Success"
        val message = "Order ID: $orderId | Status: Success"

        val intent = Intent(activity, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(activity, 0, intent, 0)
        val notificationManagerCompat = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val mBuilder = NotificationCompat.Builder(requireActivity(), channelId)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_logo_furnitale)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(requireActivity(), R.color.float_transparent))
            .setSound(alarmSound)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            mBuilder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = mBuilder.build()
        notificationManagerCompat.notify(ORDER_SUCCESS, notification)
    }
}