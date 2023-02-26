app.controller('categoryListCtrl', function ($scope, $rootScope, $http, $window) {
	$scope.listCategory = [];
	$scope.currentPage = 0;
	$scope.itemsPerPage = 10;

	$scope.loadData = function () {
		$rootScope.page($rootScope.listCategoryApi, $scope.currentPage, $scope.itemsPerPage)
			.success(function (data, status, headers, config) {
				$scope.listCategory = data.data.content;
				console.log("🚀 ~ file: category.list.page.js:10 ~ data.data.content:", data.data.content)
			})
			.error(function (data, status, header, config) {
				if (status == 404) {
					$window.location.href = "#404";
				} else if (status == 500) {
					$window.location.href = "#500";
				}
			});
	};

	$scope.loadData();

	// $scope.view = function (data) {
	// 	new Custombox.modal({
	// 		content: {
	// 			target: '#viewModal',
	// 			effect: 'flash',
	// 			positionX: 'center',
	// 			positionY: 'center',
	// 		},
	// 		overlay: {
	// 			color: '#36404a',
	// 			speedIn: 100,
	// 			speedOut: 100,
	// 		}
	// 	}).open();
	// 	$scope.categoryView = data;
	// 	$scope.listCategoryDetail = [];
	// 	$rootScope.get($rootScope.listCategoryDetailApi + '/' + data.id)
	// 		.success(function (data, status, headers, config) {
	// 			$scope.listCategoryDetail = data.data;
	// 		})
	// 		.error(function (data, status, header, config) {
	// 			if (status == 404) {
	// 				$window.location.href = "#404";
	// 			} else if (status == 500) {
	// 				$window.location.href = "#500";
	// 			}
	// 		});
	// }

	// $scope.delete = function (data) {
	// 	Swal.fire(
	// 		{
	// 			title: "Bạn có muốn xoá ?",
	// 			text: "Bạn có muốn xoá danh mục " + data.name + "!",
	// 			type: "warning",
	// 			showCancelButton: !0,
	// 			confirmButtonColor: "#348cd4",
	// 			cancelButtonColor: "#6c757d",
	// 			confirmButtonText: "Xác nhận!"
	// 		})
	// 		.then(
	// 			function (t) {
	// 				if (t.value == true) {
	// 					// $rootScope.delete(, data.id)
	// 					// 	.success(function (data, status, headers, config) {
	// 					// 		Swal.fire("Xoá thành công!", "Đã xoá danh mục.", "success")
	// 					// 			.then(function () {
	// 					// 				$window.location.href = "#user";
	// 					// 			})
	// 					// 	})
	// 					// 	.error(function (data, status, header, config) {
	// 					// 		if (status == 401) {
	// 					// 			Swal.fire({
	// 					// 				title: "Không thể xoá danh mục!",
	// 					// 				text: data.message,
	// 					// 				type: "error",
	// 					// 				confirmButtonColor: "#d03f3f"
	// 					// 			})
	// 					// 		} else if (status == 404) {
	// 					// 			$window.location.href = "#404";
	// 					// 		} else if (status == 500) {
	// 					// 			$window.location.href = "#500";
	// 					// 		}
	// 					// 	});
	// 				}
	// 			})
	// }

	$scope.sortColumn = "name";
	$scope.reverseSort = false;
	$scope.pageSize = 10;

	$scope.sortData = function (column) {
		$scope.reverseSort = ($scope.sortColumn == column) ?
			!$scope.reverseSort : false;
		$scope.sortColumn = column;
	}

	$scope.getSortClass = function (column) {
		if ($scope.sortColumn == column) {
			return $scope.reverseSort ? 'fa fa-arrow-down' : 'fa fa-arrow-up';
		}
		return '';
	}
});
