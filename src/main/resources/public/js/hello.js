var officaApp = angular.module('office', ["ngRoute"]);

officaApp.config(function ($routeProvider) {
    $routeProvider
        .when('/',
            {
                controller: 'HomeController',
                templateUrl: 'partials/ViewHome.html'
            })
        .when('/visits',
            {
                controller: 'AllVisitsController',
                templateUrl: '/partials/ViewAllVisits.html'
            })
        .when('/register',
            {
                controller: 'RegistrationController',
                templateUrl: '/partials/Register.html'
            })
        .otherwise( {redirectTo: '/'});
})

officaApp.controller('AllVisitsController', function($scope, $http, $rootScope) {
    console.log("in");
    $http
        .get('/api/visit/get/all')
        .then(function(response) {
            $scope.visits = response.data;
    });
    $http
        .get('/api/visit/count/today')
        .then(function(response) {
            $rootScope.todayVisits = response.data;
        });
});

officaApp.controller('HomeController', function($scope, $http) {
    console.log("home");
})