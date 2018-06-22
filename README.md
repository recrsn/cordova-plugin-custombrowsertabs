# cordova-plugin-custombrowsertabs

This plugin provides an interface to customizable in-app browser tabs that exist on some mobile platforms, specifically Custom Tabs on Android.

Currently supports only Android.

## Installation

```
npm install cordova-plugin-custombrowsertabs
```

This library depends on [com.android.support:customtabs:25.1.0+](https://developer.android.com/topic/libraries/support-library/packages#custom-tabs). If you are using any other plugin that utilized support libraries, make sure that they use the same library version. If you get build failures, try using [cordova-android-support-gradle-release](https://github.com/dpa99c/cordova-android-support-gradle-release) plugin to align all support library versions.


## Usage

```js

// Checks if custom browser functionality is available. Callback value is true
// if any browser installed on the device supports the custom tab protocol,
// false if otherwise.
//
// NOTE: You should always check if the functionality is available on the user
// device. The library does not fallback to a default implementation. It is left
// to the developer to fallback using an in-app browser, or the system browser.
cordova.CustomBrowser.available(function (result) {
    console.log(result ? 'Supported' : 'Not supported');
});

const options = {
    // Primary color for tab toolbar
    // accepts a value in HTML color notation: #rrggbb
    toolbarColor: '#000000',

    // Secondary (text) color for tab toolbar
    // accepts a value in HTML color notation: #rrggbb
    secondaryToolbarColor: '#ffffff',

    // If true, shows a default share menu item. Defaults to false
    showShareMenuItem: false,

    // If true shows page title. Defaults to true
    showTitle: true,

    // If true, hides the URL barwhen the user scrolls down the page content.
    // Defaults to false
    enableUrlHiding: false,
};

cordova.CustomBrowser.open(document.getElementById('url').value, options, function () {
    console.log('Success');
}, function(err) {
    console.error(err);
});
```

## LICENSE

Copyright 2018 Amitosh Swain Mahapatra. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the
License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing permissions and
limitations under the License.

