(function() {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('MatchDetailController', MatchDetailController);

    MatchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Match'];

    function MatchDetailController($scope, $rootScope, $stateParams, entity, Match) {
        var vm = this;
        vm.match = entity;
        vm.load = function (id) {
            Match.get({id: id}, function(result) {
                vm.match = result;
            });
        };
        var unsubscribe = $rootScope.$on('bookmakerApp:matchUpdate', function(event, result) {
            vm.match = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
