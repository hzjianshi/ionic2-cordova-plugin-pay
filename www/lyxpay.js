var exec = require('cordova/exec');

var Pay = function() {};
Pay.prototype.pay = function(pay_type,pay_param,pay_callbacks) {
    exec(function () {
        pay_callbacks.success();
    }, function (error_essage) {
        pay_callbacks.failure(error_essage);
    }, "Lyxpay", "pay", [pay_type,pay_param]);
};

module.exports = new Pay();