/**
 * Created by mf57 on 22.05.2016.
 */
(function() {
    'use strict';
    angular
        .module('bookmakerApp')
        .factory('BookMaking', BookMaking);

    BookMaking.$inject = ['$resource'];

    function BookMaking ($resource) {
        var resourceUrl =  'api/matches/in_future';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
