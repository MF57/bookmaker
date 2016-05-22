/**
 * Created by mf57 on 22.05.2016.
 */
(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('BookListingController', BookListingController);

    BookListingController.$inject = ['$scope', '$state', 'Book'];

    function BookListingController ($scope, $state, Book) {
        var vm = this;
        vm.message = "Here are all of your books";
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
