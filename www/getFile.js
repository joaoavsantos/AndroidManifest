var exec = require('cordova/exec');

var PLUGIN_NAME = 'GetFilePlugin';

exports.getFile = function (success, error) {
  exec(success, error, PLUGIN_NAME, "getFile", []);
};
