/*
 * Copyright (c) 2012. Mohan Ambalavanan
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *    Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *  limitations under the License
 */

/**
 * Created with IntelliJ IDEA.
 * User: mohan
 * Date: 5/05/12
 * Time: 6:12 PM
 * To change this template use File | Settings | File Templates.
 */

//Entry Point for all our Java Script On Load
//globalCache

//Enterprise JQueryNameSpace aka Module PAttern as
// defined in  http://enterprisejquery.com/2010/10/how-good-c-habits-can-encourage-bad-javascript-habits-part-1/
(function (creativeConfigurationAPI, $, undefined) {
    //public function
    creativeConfigurationAPI.main = function () {
        $(document).ready(function () {

        });
    };
    creativeConfigurationAPI.animateMainContentOnLoad = function (data) {
        switch (data.status) {
            case "begin":
                break;
            case "complete":
                break;
            case "success":
                animateMainContentRight();
                break;
        }
    };
    function animateMainContentRight() {
        console.log("Animating Main Content Right");
        $('#mainContent').show("slide", { direction:"right" }, 700);
    }
}(window.creativeConfigurationAPI = window.creativeConfigurationAPI || {}, jQuery));


