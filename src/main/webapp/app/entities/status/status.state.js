(function() {
    'use strict';

    angular
        .module('springMvcAsyncStatusApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('status', {
            parent: 'entity',
            url: '/status',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/status/status.html',
                    controller: 'StatusCtrl',
                    controllerAs: 'vm'
                }
            }

        })
    }

})();
