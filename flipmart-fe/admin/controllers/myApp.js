var app = angular.module('myApp', ['ngRoute', 'ngStorage']);
app.run(function ($rootScope, $http, $window, $localStorage) {

    $rootScope.$on('$routeChangeStart', function () {
        $rootScope.loading = true;
    });

    $rootScope.$on('$routeChangeSuccess', function () {
        $rootScope.loading = false;
    });

    $rootScope.$on('$routeChangeError', function () {
        $rootScope.loading = false;
    });

    $rootScope.userLogin = JSON.parse(localStorage.getItem("userLogin"));

    if ($window.sessionStorage.token == undefined || $rootScope.userLogin == null) {
        $window.location.href = '#login';
    }
    else {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $window.sessionStorage.token;
        window.location.href = '#home';
    }

    $rootScope.baseApi = 'http://localhost:8080';
    $rootScope.clientApi = 'http://127.0.0.1:5500';

    $rootScope.loginApi = '/api/auth/login';
    $rootScope.registerApi = '/api/auth/register';

    $rootScope.listUserApi = '/api/user';
    $rootScope.createUserApi = '/api/user/create';
    $rootScope.editUserApi = '/api/user/edit';
    $rootScope.deleteUserApi = '/api/user/delete/';

    $rootScope.listCategoryApi = '/api/category';
    $rootScope.createCategoryApi = '/api/category/create';

    $rootScope.listCategoryDetailApi = '/api/category-detail';

    $rootScope.listColorApi = '/api/color';
    $rootScope.createColorApi = '/api/color/create';
    $rootScope.deleteColorApi = '/api/color/delete/';

    $rootScope.listBrandApi = '/api/brand';
    $rootScope.createBrandApi = '/api/brand/create';
    $rootScope.deleteBrandApi = '/api/brand/delete/';

    $rootScope.createProductApi = '/api/product/create';
    $rootScope.listProductApi = '/api/product';

    $rootScope.logout = function () {
        localStorage.clear();
        Swal.fire({
            title: "Thông báo!",
            text: "Đã đăng xuất thành công!",
            type: "success",
            confirmButtonColor: "#348cd4"
        }).then(function () {
            //$window.location.href = "#login";
            $window.location.reload();
        })
    }

    $rootScope.login = function(url, data){
        return $http({
            method: "POST",
            url: $rootScope.baseApi + url,
            data: data,
            headers: {
                'Content-Type': 'application/json;',
                'Access-Control-Allow-Origin': true,
                'X-Requested-With': 'XMLHttpRequest'
            }
        });
    }

    $rootScope.register = function(url, data){
        return $http({
            method: "POST",
            url: $rootScope.baseApi + url,
            data: data,
            headers: {
                'Content-Type': 'application/json;',
                'Access-Control-Allow-Origin': true,
                'X-Requested-With': 'XMLHttpRequest'
            }
        });
    }

    $rootScope.get = function (url) {
        return $http({
            method: "GET",
            url: $rootScope.baseApi + url,
            headers: {
                'Content-Type': 'application/json;',
                'Access-Control-Allow-Origin': true,
                'X-Requested-With': 'XMLHttpRequest',
                'Authorization': 'Bearer ' + $window.sessionStorage.token
            }
        });
    }

    $rootScope.post = function (url, data) {
        return $http({
            method: "POST",
            url: $rootScope.baseApi + url,
            data: data,
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined,
                'Access-Control-Allow-Origin': true,
                'X-Requested-With': 'XMLHttpRequest',
                'Authorization': 'Bearer ' + $window.sessionStorage.token
            }
        });
    }

    $rootScope.delete = function (url, data) {
        return $http({
            method: "DELETE",
            url: $rootScope.baseApi + url,
            data: data,
            transformRequest: angular.identity,
            headers: {
                'Access-Control-Allow-Origin': true,
                'X-Requested-With': 'XMLHttpRequest',
                'Authorization': 'Bearer ' + $window.sessionStorage.token
            }
        });
    }

    $rootScope.page = function (url, page, size) {
        return $http({
            method: "GET",
            url: $rootScope.baseApi + url + '/page?page=' + page + '&size=' + size,
            headers: {
                'Content-Type': 'application/json;',
                'Access-Control-Allow-Origin': true,
                'X-Requested-With': 'XMLHttpRequest',
                'Authorization': 'Bearer ' + $window.sessionStorage.token
            }
        });
    }
});

app.config(function ($routeProvider) {
    $routeProvider
        .when('/404', { templateUrl: '../admin/auth/error/404.html', controller: '404Ctrl' })
        .when('/500', { templateUrl: '../admin/auth/error/500.html', controller: '500Ctrl' })

        .when('/home', { templateUrl: '../admin/home/index.html', controller: 'homeCtrl' })

        .when('/login', { templateUrl: '../admin/auth/login.html', controller: 'loginCtrl' })
        .when('/register', { templateUrl: '../admin/auth/register.html', controller: 'registerCtrl' })

        .when('/user', { templateUrl: '../admin/user/list.html', controller: 'userListCtrl' })
        .when('/user-form', { templateUrl: '../admin/user/form.html', controller: 'userFormCtrl' })

        .when('/category', { templateUrl: '../admin/category/list.html', controller: 'categoryListCtrl' })
        .when('/category-form', { templateUrl: '../admin/category/form.html', controller: 'categoryFormCtrl' })

        .when('/color', { templateUrl: '../admin/color/listAndForm.html', controller: 'colorCtrl' })

        .when('/brand', { templateUrl: '../admin/brand/listAndForm.html', controller: 'brandCtrl' })

        .when('/product', { templateUrl: '../admin/product/list.html', controller: 'productListCtrl' })
        .when('/product-form', { templateUrl: '../admin/product/form.html', controller: 'productFormCtrl' })

        .otherwise({ redirectTo: '/login' });
});

app.directive('pagination', function () {
	return {
		restrict: 'E',
		templateUrl: 'fragments/pagination.html',
		scope: {
			itemsPerPage: '=',
			data: '=',
			currentPage: '=',
			onPageChange: '&'
		},
		link: function (scope) {
			scope.totalPages = Math.ceil(scope.data.length / scope.itemsPerPage);
			scope.range = function (start, end) {
				var res = [];
				for (var i = start; i <= end; i++) {
					res.push(i);
				}
				return res;
			};
			scope.prevPage = function () {
				if (scope.currentPage > 1) {
					scope.currentPage--;
					scope.onPageChange();
				}
			};
			scope.nextPage = function () {
				if (scope.currentPage < scope.totalPages) {
					scope.currentPage++;
					scope.onPageChange();
				}
			};
			scope.setPage = function (page) {
				if (page >= 1 && page <= scope.totalPages) {
					scope.currentPage = page;
					scope.onPageChange();
				}
			};
		}
	};
});
