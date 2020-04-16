package sa.tfe.applauncherplugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import java.util.Iterator;
import android.net.Uri;
/**
 * This class echoes a string called from JavaScript.
 */
public class applauncherplugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
           // String message = args.getString(0);
		   
            this.coolMethod(args, callbackContext);
            return true;
        }
        return false;
    }
	
	private void launchAppWithData( String packageName, final String uri, final String dataType, final Bundle extras)
    {
		final CordovaInterface mycordova = cordova;
		final CordovaPlugin plugin = this;
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        if (dataType != null) {
//            intent.setDataAndType(Uri.parse(uri), dataType);
//        } else {
//            intent.setData(Uri.parse(uri));
//        }
//
//        if (packageName != null && !packageName.equals("")) {
//            intent.setPackage(packageName);
//        }
        PackageManager pm =webView.getContext().getPackageManager();
       //pm = getLaunchIntentForPackage(packageName);
        intent = pm.getLaunchIntentForPackage(packageName);
        //intent.putExtras(extras);
		 intent.putExtra("zoombundle",extras);

        try
        {
            mycordova.startActivityForResult(plugin, intent, 0);
        }
        catch (Exception e)
        {
            Log.d("applauncher","activity not found:"+e);
        }

    }


    private void coolMethod(JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		final JSONObject options = args.getJSONObject(0);
		
		Bundle bundle = new Bundle();
		Iterator iter = options.keys();
		
		while(iter.hasNext()){
        String key = (String)iter.next();
        String value = options.getString(key);
        bundle.putString(key,value);
    }
	
			String packageTobeFound =  options.getString("packagename");
		
		 try {
			
            PackageManager packageManager =webView.getContext().getPackageManager();
            packageManager.getPackageInfo(packageTobeFound, 0);
            Log.d("AppLauncher","...this package is available");
			 launchAppWithData(packageTobeFound,null,null,bundle);
			 callbackContext.success("Package found");
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("AppLauncher","...this is not available ");
			getOneteamLive(packageTobeFound,callbackContext);
        }
      //  if (message != null && message.length() > 0) {
        //    callbackContext.success(message);
        //} else {
         //   callbackContext.error("Expected one non-empty string argument.");
        //}
    }
	
	private void getOneteamLive(String packageToDownload, CallbackContext callbackContext) {
		
		try{
			
			final CordovaInterface mycordova = cordova;
			final CordovaPlugin plugin = this;
			
			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+packageToDownload));
			mycordova.startActivityForResult(plugin, intent, 0);
			
		}
		catch(Exception e)
		{
			callbackContext.error("Application is not available on PlayStore");
		}
		
	}
}
