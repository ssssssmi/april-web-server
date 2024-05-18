angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189';

    $scope.fillTable = function () {
        $http.get(contextPath + '/items')
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/item', $scope.newProduct)
            .then(function (response) {
                $http.post(contextPath + '/item', $scope.newProduct)
                $scope.fillTable();
            });
    };

    $scope.deleteProductById = function (productId) {
        console.log('deleteTest');
        $http.delete(contextPath + '/item?id=' + productId)
            .then(function (response) {
                $scope.fillTable();
            });
    };

    $scope.fillTable();
});