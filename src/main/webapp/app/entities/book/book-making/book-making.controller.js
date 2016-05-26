/**
 * Created by mf57 on 22.05.2016.
 */
(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('BookMakingController', BookMakingController);

    BookMakingController.$inject = ['$scope', '$state', 'BookMaking'];

    function BookMakingController ($scope, $state, BookMaking) {
        var vm = this;
        vm.message = "Here you can make new books";
        vm.matches = [];
        vm.loadAll = function() {
            BookMaking.query(function(result) {
                vm.matches = result;
                console.log(vm.matches);
            });
        };

        vm.loadAll();

    }
})();
