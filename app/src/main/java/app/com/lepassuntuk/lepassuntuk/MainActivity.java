package app.com.lepassuntuk.lepassuntuk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import app.com.lepassuntuk.lepassuntuk.activity.ErrorActivity;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private BottomNavigationView bottomNavigationView;
    private Bundle webViewBundle;
    private ProgressBar progressBar;
    private boolean doubleBackToExitPressedOnce = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.webview_main);
        bottomNavigationView = findViewById(R.id.navigation);
        progressBar = findViewById(R.id.spin_kit);

        //web settings
        WebSettings mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setSupportZoom(false);
        mWebSettings.setBuiltInZoomControls(false);
        mWebSettings.setAllowContentAccess(true);
        mWebSettings.setUserAgentString(mWebSettings.getUserAgentString().replace("; wv", ""));

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(mWebViewClient);

        //bottom navigation
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //progress bar
        Sprite doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {

                case R.id.home_menu:
                    loadHome();
                    return true;
                case R.id.home_pariwisata:
                    loadWisata();
                    return true;
                case R.id.home_mobil:
                    loadMobil();
                    return true;
                case R.id.home_villa:
                    loadVila();
                    return true;
                case R.id.home_akun:
                    loadAkun();
                    return true;
            }
            return false;
        }
    };

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,
                                                String url) {
            if (url != null && url.startsWith("whatsapp://")) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } else if (url != null && url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
                view.reload();
                return true;
            } else {
                view.loadUrl(url);
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(getApplicationContext(), ErrorActivity.class));
            getParent().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            getParent().finish();
        }

        public void onLoadResource(WebView view, String url) {

        }

        public void onPageFinished(WebView view, String url) {
            progressBar.setVisibility(View.GONE);
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        if (webViewBundle != null) {
            mWebView.restoreState(webViewBundle);
        } else {
            loadHome();
        }
    }

    private void loadHome() {

        mWebView.loadUrl("https://m.lepassuntuk.com");

    }

    private void loadMobil() {

        mWebView.loadUrl("https://m.lepassuntuk.com/carrentals");

    }

    private void loadWisata() {

        mWebView.loadUrl("https://m.lepassuntuk.com/tours");

    }

    private void loadVila() {

        mWebView.loadUrl("https://m.lepassuntuk.com/villas");

    }

    private void loadAkun() {
        mWebView.loadUrl("https://m.lepassuntuk.com/login");

    }


    @Override
    protected void onStop() {
        super.onStop();
        webViewBundle = new Bundle();
        mWebView.saveState(webViewBundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewBundle = new Bundle();
        mWebView.saveState(webViewBundle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public boolean canGoBack() {
        return mWebView != null && mWebView.canGoBack();
    }

    public void goBack() {
        if(mWebView != null) {
            mWebView.goBack();
        }
    }
}
