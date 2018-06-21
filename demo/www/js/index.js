/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function () {
        document.getElementById('open-browser').addEventListener('click', this.openBrowser.bind(this));
        document.getElementById('available').addEventListener('click', this.checkAvailability.bind(this));
    },

    openBrowser: function () {
        var options = {
            toolbarColor: document.getElementById('toolbarColor').value,
            secondaryToolbarColor: document.getElementById('secondaryToolbarColor').value,
            showShareMenuItem: document.getElementById('showShareMenuItem').checked,
            showTitle: document.getElementById('showTitle').checked,
            enableUrlHiding: document.getElementById('enableUrlHiding').checked,
        };

        cordova.CustomBrowser.open(document.getElementById('url').value, options, function () {
            console.log('Success');
        }, function (err) {
            console.error(err);
        });
    },

    checkAvailability: function () {
        cordova.CustomBrowser.available(function (result) {
            alert(result ? 'Supported' : 'Not supported');
        }, function (err) {
            console.error(err);
        });
    }
};

app.initialize();