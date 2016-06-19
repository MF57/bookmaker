/**
 * Created by mf57 on 22.05.2016.
 */
(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('BookListingController', BookListingController);

    BookListingController.$inject = ['$scope', '$state', 'BookListing'];

    function BookListingController ($scope, $state, BookListing) {
        var vm = this;
        vm.message = "Here are all of your books";
        vm.books = [];
        vm.loadAll = function() {
            BookListing.query(function(result) {
                vm.books = result;
                console.log(vm.books);
            });
        };

        vm.loadAll();

    }
})();
