var exec = require('cordova/exec');
var PLUGIN_NAME = 'flashlight';

var flashlight = {

	hasFlashlight : function (success, error ) {
		exec(success, error, 'flashlight', 'hasFlashlight', []);
	},
	switchon : function (success, error ) {
		exec(success, error, 'flashlight', 'switchon', []);
	},
	switchoff : function (success, error ) {
		exec(success, error, 'flashlight', 'switchoff', []);
	}
};

module.exports = flashlight;
