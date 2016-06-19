/**
 * Created by mf57 on 22.05.2016.
 */
(function () {
    'use strict';

    angular
        .module('bookmakerApp')
        .controller('BookMakingController', BookMakingController);

    BookMakingController.$inject = ['$scope', '$state', 'Book', 'BookMaking', 'Account'];

    function BookMakingController($scope, $state, Book, BookMaking, Account) {
        var vm = this;
        vm.message = "Here you can make new books";
        vm.matches = [];
        vm.loggedUser  = [];

        vm.bookMatches = function () {
            var bookedMatches = vm.matches.filter(function (match) {
                return match.team2ScorePrediction !== undefined && match.team1ScorePrediction !== undefined
            });
            bookedMatches.forEach(function (match) {
                var book = {};
                book.matchId = match.id;
                book.score1Prediction = match.team1ScorePrediction;
                book.score2Prediction = match.team2ScorePrediction;
                book.userId = vm.loggedUser.resource.login;
                vm.matches = vm.matches.filter(function (m) {
                    return m.id !== match.id;
                });
                vm.save(book);
            })
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('bookmakerApp:bookUpdate', result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function (book) {
            vm.isSaving = true;
            if (book.id !== null) {
                Book.update(book, onSaveSuccess, onSaveError);
            } else {
                Book.save(book, onSaveSuccess, onSaveError);
            }
        };


        vm.loadAll = function () {
            BookMaking.query(function (result) {
                vm.matches = result;
            });
            Account.get(function (result) {
                vm.loggedUser = result;
            })
        };

        vm.loadAll();

    }
})();
