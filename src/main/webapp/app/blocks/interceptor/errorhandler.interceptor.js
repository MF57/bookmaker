(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .factory('errorHandlerInterceptor', errorHandlerInterceptor);

    errorHandlerInterceptor.$inject = ['$q', '$rootScope'];

    function errorHandlerInterceptor ($q, $rootScope) {
        var service = {
            responseError: responseError
        };

        return service;

        function responseError (response) {
            if (!(response.status === 401 && (response.data === '' || response.data.path.indexOf('/api/account') === 0 ))) {
                $rootScope.$emit('bookmakerApp.httpError', response);
            }
            return $q.reject(response);
        }
    }
})();
