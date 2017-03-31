package tk.forest_tales.gmeter;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.io.InputStreamReader;

public class PrinterService {

    private Context context = null;
    private ReportData report = null;

    private WebView mWebView;

    PrinterService(Context _context, ReportData _report){
        context = _context;
        report = _report;
    }

    public void print(){
        try {
            Template tmpl = Mustache.compiler().compile(new InputStreamReader(context.getAssets().open("report.html")));

            WebView webView = new WebView(context);
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    createWebPrintJob(view);
                    mWebView = null;
                }
            });

            webView.loadDataWithBaseURL(null, tmpl.execute( report ), "text/HTML", "UTF-8", null);

        }catch(IOException e){
            Log.d("meter","Print error");
        }
    }

    private void createWebPrintJob(WebView webView) {
        PrintManager printManager = (PrintManager)context.getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter("Month Report");

        String jobName = context.getString(R.string.app_name) + " Document";
        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }

}
