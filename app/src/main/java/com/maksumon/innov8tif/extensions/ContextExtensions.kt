package com.maksumon.innov8tif.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import com.maksumon.innov8tif.activities.BaseActivity

fun Context.isInternetAvailable(): Boolean {
    val result: Boolean
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities =
        connectivityManager.activeNetwork ?: return false
    val activeNetwork =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    if (!result) this.showErrorToast("Internet not available. Please try again!!")
    return result
}

fun Context.showErrorToast(message: String?) {
    try {
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        toast.show()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.showProgressBar() {
    try {
        val activity = this as BaseActivity
        activity.showProgressDialog()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.hideProgressBar() {
    try {
        val activity = this as BaseActivity
        activity.hideProgressDialog()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
