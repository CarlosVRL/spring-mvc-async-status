(function() {

    'use strict';

    angular
        .module('springMvcAsyncStatusApp')
        .controller('StatusCtrl', StatusCtrl);

    StatusCtrl.$inject = [
        '$log',
        '$timeout',
        '$state',
        'Status'
    ];

    function StatusCtrl(
        $log,
        $timeout,
        $state,
        Status
    ) {
        var vm = this;

        //
        // Model
        //
        vm.data = [];
        vm.SECONDS_AS_MILLI = 5000;

        load();
        function load() {
            getStatus();
            $timeout(function() {
                if ($state.current.name == "status" && vm.data != null) {
                    load();
                }
            }, vm.SECONDS_AS_MILLI);
        };

        //
        // Controller
        //
        vm.addJob = function() {
            Status.addJob()
                .then(function success(res) {
                    $log.debug(res);
                }, function error(err) {
                    $log.debug(err)
                });
            getStatus();
        }

        //
        // Implementation
        //
        function getStatus() {
            Status.getStatus()
                .then(function success(res) {
                    vm.data = res.data;
                }, function error(err) {
                    $log.debug(err);
                    vm.data = null;
                });
        }
    }
})();
