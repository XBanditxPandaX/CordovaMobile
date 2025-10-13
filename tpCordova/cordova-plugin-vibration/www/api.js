const exec = require('cordova/exec');

module.exports = {
    vibrate: function(pattern, successCallback, errorCallback) {
        exec(successCallback, errorCallback, "Vibration", "vibrate", [pattern]);
    }
};