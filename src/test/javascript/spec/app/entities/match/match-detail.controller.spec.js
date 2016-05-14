'use strict';

describe('Controller Tests', function() {

    describe('Match Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockMatch;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockMatch = jasmine.createSpy('MockMatch');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Match': MockMatch
            };
            createController = function() {
                $injector.get('$controller')("MatchDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bookmakerApp:matchUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
