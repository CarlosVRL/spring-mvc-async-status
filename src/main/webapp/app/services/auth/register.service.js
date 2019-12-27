(function () {
    'use strict';

    angular
        .module('springMvcAsyncStatusApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
