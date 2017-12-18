angular.module('crudApp').factory('ConfigService',
    ['$http', '$q', 'urls',
        function ($http, $q, urls) {

            var factory = {
                getFacebookData: function () {
                    var deferred = $q.defer();
                    FB.api('/me', {
                       fields: 'id,first_name,last_name,email,link,gender,picture'
                    }, function(response) {
                        if (!response || response.error) {
                            deferred.reject('Error occured');
                        } else {
                            deferred.resolve(response);
                        }
                    });
                    return deferred.promise;
                },
                loginFB: function () {
                    var deferred = $q.defer();
                    FB.login(function(response) {
                        if (!response || response.error) {
                            deferred.reject('Error occured');
                        } else {
                            deferred.resolve(response);
                        }
                    });
                    return deferred.promise;
                },
                login: function (token) {
                    var deferred = $q.defer();
                    $http.post(urls.USER_SERVICE_API + "login", token)
                        .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            deferred.reject(errResponse);
                        }
                    );
                    return deferred.promise;
                },
                testSecurity: function () {
                    var deferred = $q.defer();
                    $http.get(urls.USER_SERVICE + "test")
                        .then(
                        function (response) {
                            deferred.resolve(response.data);
                        },
                        function (errResponse) {
                            deferred.reject(errResponse);
                        }
                    );
                    return deferred.promise;
                }


            };

            return factory;


        }
    ]);