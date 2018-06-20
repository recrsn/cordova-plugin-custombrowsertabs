/*
 * Copyright 2018 Amitosh Swain Mahapatra. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package in.amitosh.custombrowser;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsService;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.Iterator;
import java.util.List;

public class CustomBrowserPlugin extends CordovaPlugin {

    private static final String TAG = "CustomBrowserPlugin";
    private boolean customTabAvailable = false;
    private boolean cached = false;

    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        Log.d(TAG, "Initializing CustomBrowserPlugin");
    }

    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        switch (action) {
            case "open":
                this.open(args.getString(0), callbackContext);
                return true;
            case "available":
                this.available(callbackContext);
                return true;
            default:
                return false;
        }
    }

    private void open(String url, CallbackContext callbackContext) {
        if (url == null || url.isEmpty()) {
            callbackContext.error("Expected a valid URI");
            return;
        }


        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                .addDefaultShareMenuItem();
        CustomTabsIntent customTabsIntent = builder.build();

        try {
            customTabsIntent.launchUrl(cordova.getActivity(), Uri.parse(url));
            callbackContext.success();
        } catch (ActivityNotFoundException ex) {
            callbackContext.error(ex.getMessage());
        }

    }

    private void available(CallbackContext callbackContext) {
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, this.isCustomTabAvailable()));
    }

    /* The following code is adapted from: https://github.com/google/cordova-plugin-browsertab */

    /*
     * Copyright 2016 Google Inc. All Rights Reserved.
     *
     * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
     * in compliance with the License. You may obtain a copy of the License at
     *
     * http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software distributed under the
     * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
     * express or implied. See the License for the specific language governing permissions and
     * limitations under the License.
     */

    private boolean isCustomTabAvailable() {
        if (cached) {
            return customTabAvailable;
        }

        PackageManager pm = cordova.getActivity().getPackageManager();
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));

        List<ResolveInfo> activities = pm.queryIntentActivities(webIntent, PackageManager.GET_RESOLVED_FILTER);

        for (ResolveInfo info : activities) {
            if (!isBrowser(info)) {
                continue;
            }

            if (hasCustomTabService(pm, info.activityInfo.packageName)) {
                this.customTabAvailable = true;
                this.cached = true;
            }
        }

        return this.customTabAvailable;
    }


    private boolean isBrowser(ResolveInfo info) {
        if (!info.filter.hasAction(Intent.ACTION_VIEW)
                || !info.filter.hasCategory(Intent.CATEGORY_BROWSABLE)
                || info.filter.schemesIterator() == null) {
            return false;
        }

        if (info.filter.authoritiesIterator() != null) {
            return false;
        }

        boolean supportsHttp = false;
        boolean supportsHttps = false;

        Iterator<String> i = info.filter.schemesIterator();

        while (i.hasNext()) {
            String scheme = i.next();

            supportsHttp |= "http".equals(scheme);
            supportsHttps |= "https".equals(scheme);

            if (supportsHttp && supportsHttps) {
                return true;
            }
        }

        return false;
    }

    private boolean hasCustomTabService(PackageManager pm, String packageName) {
        Intent serviceIntent = new Intent();
        serviceIntent.setAction(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION);
        serviceIntent.setPackage(packageName);
        return (pm.resolveService(serviceIntent, 0) != null);
    }
}

