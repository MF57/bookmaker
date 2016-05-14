(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('BookController', BookController);

    BookController.$inject = ['$scope', '$state', 'Book'];

    function BookController ($scope, $state, Book) {
        var vm = this;
        vm.books = [];
        vm.loadAll = function() {
            Book.query(function(result) {
                vm.books = result;
            });
        };

        vm.loadAll();
        
    }
})();
