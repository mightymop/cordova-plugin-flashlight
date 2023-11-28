```
package de.mopsdom.flashlight;

import android.content.Context;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class flashlight extends CordovaPlugin {

    private final String pluginName = "cordova-plugin-flashlight";
	
	private static CameraManager cameraManager;
    private static String cameraId;
    private static boolean isFlashlightOn = false;
	private static boolean hasFlashlight = false;

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);

		cameraManager = (CameraManager) cordova.getActivity().getSystemService(Context.CAMERA_SERVICE);
		try {
			// Überprüfen, ob die Kamera-LED unterstützt wird
			if (cordova.getActivity().getPackageManager().hasSystemFeature(android.content.pm.PackageManager.FEATURE_CAMERA_FLASH)) {
				cameraId = cameraManager.getCameraIdList()[0];
				flashlight.hasFlashlight=true;
			} else {
				flashlight.hasFlashlight=false;
			}
		} catch (CameraAccessException e) {
			e.printStackTrace();
			flashlight.hasFlashlight=false;
		}
	}

	@Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {

	
		if (action.equals("hasFlashlight")) {
              
			if (!flashlight.hasFlashlight)
			{
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Flashlight wird nicht unterstützt."));
			}
			else
			{				
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
			}					
				
            return true;
        }
		else
        if (action.equals("switchon")) {
              try {
					if (isFlashlightOn)
					{
						callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Flashlight ist bereits aktiviert."));
					}
					else
					{
						cameraManager.setTorchMode(cameraId, true);
						isFlashlightOn = true;
						callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
					}					
				} catch (CameraAccessException e) {
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, e.getMessage()));
				}
            return true;
        }
		else
		if (action.equals("switchoff")) {
            
			try {
				if (!isFlashlightOn)
				{
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, "Flashlight ist bereits deaktiviert."));
				}
				else
				{
					cameraManager.setTorchMode(cameraId, false);
					isFlashlightOn = false;
					callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK));
				}
			} catch (CameraAccessException e) {
				callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.ERROR, e.getMessage()));
			}
            return true;
        }
        else
        {
            return false;
        }
    }
}
```