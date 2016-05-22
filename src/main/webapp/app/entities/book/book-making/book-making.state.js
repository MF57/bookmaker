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
        $stateProvider.state('make_books', {
            parent: 'app',
            url: '/makeBooks',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Make Books'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/book/book-making/book-making.html',
                    controller: 'BookMakingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
