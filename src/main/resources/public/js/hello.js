var officaApp = angular.module('office', ['ngRoute']);

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
        .when('/newUser',
            {
                controller: 'NewUserController',
                templateUrl: '/partials/NewUser.html'
            })
        .when('/updateVisit',
            {
                controller: 'UpdateVisitController',
                templateUrl: '/partials/UpdateVisit.html'
            })
        .when('/changePassword',
            {
                controller: 'ChangePasswordController',
                templateUrl: '/partials/ChangePassword.html'
            })
        .when('/profile',
            {
                controller: 'ProfileController',
                templateUrl: '/partials/Profile.html'
            })
        .when('/myVisits',
            {
                controller: 'MyVisitController',
                templateUrl: '/partials/MyVisits.html'
            })
        .otherwise( {redirectTo: '/'});
})

officaApp.service('visitService', function() {
    var visit = {};

    var addVisit = function(v) {
        visit = v;
    }

    var getVisit = function() {
        return visit;
    }

    return {
        addVisit: addVisit,
        getVisit: getVisit
    };
});

officaApp.controller('ChangePasswordController', function($scope, $rootScope, $http) {
    $scope.changePassword = {};
    $scope.change = function() {
        if ($scope.changePassword.newPassword === $scope.newPasswordRepeated) {
            $scope.changePassword.pesel = $rootScope.user.pesel;
            var req = {
                method: 'PUT',
                url: '/password/changePassword',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.changePassword
                };

            $http(req)
                .then(function successCallback(response) {
                    $rootScope.authenticated = false;
                    $rootScope.user = {};
                }, function errorCallback(response) {
                    console.log(response);
                    $scope.success = response.data.message;
                });
        } else {
            $scope.success = "Podane hasła się różnią."
        }
    }

});

officaApp.controller('ProfileController', function($scope, $rootScope, $http) {
    $scope.profile = {};
    angular.copy($rootScope.user, $scope.profile);
    $scope.update = function() {
            var req = {
                method: 'PUT',
                url: '/api/users/update/me',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: $scope.profile
                };

            $http(req)
                .then(function successCallback(response) {
                    $rootScope.user.email = response.data.email;
                    $rootScope.user.phoneNumber = response.data.phoneNumber;
                }, function errorCallback(response) {
                    $scope.success = response.data.message;
                });
        };
});

officaApp.controller('LoginController', function($scope, $rootScope, $location, $httpParamSerializer, $http){
    $scope.credentials = {};
    $scope.loginFailed = false;
    $scope.resetPassword = function() {
        $rootScope.changePassword = true;
    };
    $scope.login = function() {
        var req = {
            method: 'POST',
            url: '/api/login',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            data: $httpParamSerializer($scope.credentials)
            };

        $http(req)
            .then(function successCallback(response) {
                $rootScope.authenticated = true;
                $rootScope.user = response.data;
                $location.path('/');
                $scope.loginFailed = false;
            }, function errorCallback(response) {
                $scope.loginFailed = true;
            });
        $scope.credentials = {};
    };
    $scope.logout = function() {
        $http
            .post('/api/logout', {})
            .success(function(response) {
                $rootScope.authenticated = false;
                $rootScope.user = {};
            })
            .error(function(err) {
                $rootScope.authenticated = true;
            });
    }

});

officaApp.controller('PasswordController', function($scope, $rootScope, $http){
    $scope.pesel = "";
    $scope.reset = function() {
        $http
            .get('/password/forgotten/'+$scope.pesel)
            .success(function(response) {
                $rootScope.changePassword = false;
                $scope.error = "";
                $scope.pesel = "";
            })
            .error(function(err) {
                $scope.error = err.message;
            })
    };
});

officaApp.controller('UpdateVisitController', function($scope, $http, visitService, $location) {
    $scope.visit = visitService.getVisit();
    visitService.addVisit({});

    $scope.updateVisit = function(visit) {
        console.log(visit);
        $http
            .put('/api/visit/update', visit)
            .success(function(response) {
                $location.path('/visits');
                visitService.addVisit({});
            })
            .error(function(err) {
                $scope.success = "Wystąpił niespodziewany błąd.";
            });
    };
});

officaApp.controller('NewUserController', function($scope, $http, $rootScope) {
    $scope.user = {}
    $scope.user.gender = 'MALE';
    $scope.RadioChange = function(s) {
        $scope.user.gender = s;
    }
    $scope.newUser = function(user) {
        user.password = "password";
        $http
            .post('/api/users/addUser', user)
            .success(function(response) {
                $scope.success = "Użytkownik został poprawnie stworzony.";
            })
            .error(function(err) {
                $scope.success = err.message;
            })
    }

})

officaApp.controller('AllVisitsController', function($scope, $window, $http, $rootScope, visitService, $location) {
    $scope.transfer = {};
    $scope.error = false;
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
    $scope.yesterday = new Date();
    $scope.yesterday.setHours(0,0,0,0);

    $scope.edit = function(visit) {
        visitService.addVisit(visit);
        $location.path('/updateVisit');
    };
    $scope.report = function() {
        $window.open("/api/report/myVisits?from="+$scope.transfer.from.getTime()+"&to="+$scope.transfer.to.getTime());
    };

});

officaApp.controller('MyVisitController', function($scope, $http, $rootScope, visitService, $location) {
    $http
        .get('/api/visit/get/my')
        .then(function(response) {
            $scope.visits = response.data;
    });
});

officaApp.controller('HomeController', function($scope, $http, $rootScope) {
    $http
        .get('/api/visit/count/today')
        .then(function(response) {
            $rootScope.todayVisits = response.data;
    });

})

officaApp.controller('RegistrationController', function($scope, $http, $rootScope, $location) {
    $scope.register = function(params) {
        params.completed = false;
        console.log(params);
        $http
            .post('/api/visit/add', params)
            .success(function(response) {
                $location.path('/visits');
            })
            .error(function(err) {
                $scope.success = "Wystąpił niespodziewany błąd";
            })
    }
    $http
        .get('/api/visit/count/today')
        .then(function(response) {
            $rootScope.todayVisits = response.data;
        });
})