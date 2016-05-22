/**
 * Created by mf57 on 22.05.2016.
 */
(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('my_books', {
            parent: 'app',
            url: '/myBooks',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'My Books'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book/book-listing/book-listing.html',
                    controller: 'BookListingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
