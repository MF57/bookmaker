/**
 * Created by mf57 on 22.05.2016.
 */
(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('BookMakingController', BookMakingController);

    BookMakingController.$inject = ['$scope', '$state', 'Book'];

    function BookMakingController ($scope, $state, Book) {
        var vm = this;
        vm.message = "Here you can make new books";
        // vm.books = [];
        // vm.loadAll = function() {
        //     Book.query(function(result) {
        //         vm.books = result;
        //     });
        // };
        //
        // vm.loadAll();

    }
})();
