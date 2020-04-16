cordova.define("sa.tfe.applauncherplugin.applauncherplugin", function(require, exports, module) {
var exec = require('cordova/exec');

var testing = function(){};

testing.prototype.coolMethod = function (options, success, error) {
 exec(success, error, 'applauncherplugin', 'coolMethod', [options]);
};

testing.install = function () {
if (!window.plugins) {
window.plugins = {};
}
window.plugins = new testing();
return window.plugins;
};
cordova.addConstructor(testing.install);

});
