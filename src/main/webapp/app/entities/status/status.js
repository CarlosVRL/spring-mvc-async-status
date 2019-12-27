(function() {

    'use strict';

    angular
        .module('springMvcAsyncStatusApp')
        .factory('Status', Status);

    Status.$inject = ['$http'];

    function Status($http) {
        var service = {
            getStatus: getStatus,
            addJob: addJob
        };

        function getStatus() {
            return $http({
                    method: 'GET',
                    url: 'api/status',
                    isArray: true
                });
        }

        function addJob() {
            return $http({
                method: 'POST',
                url: 'api/status/add-job'
            });
        }

        return service;
    }

})();
