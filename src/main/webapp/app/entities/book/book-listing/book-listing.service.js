/**
 * Created by mf57 on 22.05.2016.
 */
(function() {
    'use strict';
    angular
        .module('bookmakerApp')
        .factory('BookListing', BookListing);

    BookListing.$inject = ['$resource'];

    function BookListing ($resource) {
        var resourceUrl =  'api/books/user';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: false}
        });
    }
})();
