(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('BookDeleteController',BookDeleteController);

    BookDeleteController.$inject = ['$uibModalInstance', 'entity', 'Book'];

    function BookDeleteController($uibModalInstance, entity, Book) {
        var vm = this;
        vm.book = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Book.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
