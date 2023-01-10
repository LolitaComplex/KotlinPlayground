package com.example.webviewground

import android.annotation.SuppressLint
import android.content.Intent
import android.net.TrafficStats
import android.net.http.SslError
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.navigation.fragment.findNavController
import com.example.webviewground.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val context = view.context
            val start = System.currentTimeMillis()
            MainActivity.start = start
            val webview = WebView(view.context)
//            webview.clearCache(true)
//            webview.clearHistory()
//            webview.clearFormData()
            val end = System.currentTimeMillis()
            Log.d("Doing", "First WebView New 耗时: ${end - start}")

//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            val packageName = context.packageName
            val intent = Intent(context, NextActivity::class.java)
            context.startActivity(intent)
        }

        val webView = binding.webView
        val settings = webView.settings
        settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest
            ): Boolean {
                Log.d("Doing", "Thread" + ": ${Thread.currentThread().name} \t url: ${request.url}")
                webView.loadUrl("https://www.baidu.com")
                return true
//                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest
            ): WebResourceResponse? {
                Log.d("Doing", "Thread" + ": ${Thread.currentThread().name} \t url: ${request.url}")
                return super.shouldInterceptRequest(view, request)
            }

            @SuppressLint("WebViewClientOnReceivedSslError")
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler,
                error: SslError?
            ) {
                handler.proceed()
                super.onReceivedSslError(view, handler, error)
            }
        }
        webView.loadUrl("https://www.baidu.com")


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}