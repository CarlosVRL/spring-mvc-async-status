(function() {

    'use strict';

    angular
        .module('springMvcAsyncStatusApp')
        .controller('StatusCtrl', StatusCtrl);

    StatusCtrl.$inject = ['$log'];

    function StatusCtrl($log) {
        var vm = this;
        $log.debug('statusCtrl active');
    }

})();
