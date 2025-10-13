cordova.define("cordova-plugin-vibration.Vibration", function(require, exports, module) {
const exec = require('cordova/exec');

module.exports = {
    vibrate: function(pattern, successCallback, errorCallback) {
        exec(successCallback, errorCallback, "Vibration", "vibrate", [pattern]);
    }
};
});
