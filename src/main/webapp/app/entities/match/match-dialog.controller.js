(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('MatchDialogController', MatchDialogController);

    MatchDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Match'];

    function MatchDialogController ($scope, $stateParams, $uibModalInstance, entity, Match) {
        var vm = this;
        vm.match = entity;
        vm.load = function(id) {
            Match.get({id : id}, function(result) {
                vm.match = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bookmakerApp:matchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.match.id !== null) {
                Match.update(vm.match, onSaveSuccess, onSaveError);
            } else {
                Match.save(vm.match, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = function(date) {
            vm.datePickerOpenStatus[date] = true;
        };
    }
})();
