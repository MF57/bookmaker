(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('TeamDetailController', TeamDetailController);

    TeamDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Team'];

    function TeamDetailController($scope, $rootScope, $stateParams, entity, Team) {
        var vm = this;
        vm.team = entity;
        vm.load = function (id) {
            Team.get({id: id}, function(result) {
                vm.team = result;
            });
        };
        var unsubscribe = $rootScope.$on('bookmakerApp:teamUpdate', function(event, result) {
            vm.team = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
