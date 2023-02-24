app.controller('categoryListCtrl', function ($scope, $rootScope, $http, $window) {
	$scope.listCategory = [];

	$rootScope.get($rootScope.listCategoryApi)
		.success(function (data, status, headers, config) {
			$scope.listCategory = data.data;
		})
		.error(function (data, status, header, config) {
			if (status == 404) {
				$window.location.href = "#404";
			} else if (status == 500) {
				$window.location.href = "#500";
			}
		});

	$scope.view = function (data) {
		new Custombox.modal({
			content: {
				target: '#viewModal',
				effect: 'flash',
				positionX: 'center',
				positionY: 'center',
			},
			overlay: {
				color: '#36404a',
				speedIn: 100,
				speedOut: 100,
			}
		}).open();
		$scope.categoryView = data;
		$scope.listCategoryDetail = [];
		$rootScope.get($rootScope.listCategoryDetailApi + '/' + data.id)
			.success(function (data, status, headers, config) {
				$scope.listCategoryDetail = data.data;
			})
			.error(function (data, status, header, config) {
				if (status == 404) {
					$window.location.href = "#404";
				} else if (status == 500) {
					$window.location.href = "#500";
				}
			});
	}

	$scope.delete = function (data) {
		Swal.fire(
			{
				title: "Bạn có muốn xoá ?",
				text: "Bạn có muốn xoá danh mục " + data.name + "!",
				type: "warning",
				showCancelButton: !0,
				confirmButtonColor: "#348cd4",
				cancelButtonColor: "#6c757d",
				confirmButtonText: "Xác nhận!"
			})
			.then(
				function (t) {
					if (t.value == true) {
						// $rootScope.delete(, data.id)
						// 	.success(function (data, status, headers, config) {
						// 		Swal.fire("Xoá thành công!", "Đã xoá danh mục.", "success")
						// 			.then(function () {
						// 				$window.location.href = "#user";
						// 			})
						// 	})
						// 	.error(function (data, status, header, config) {
						// 		if (status == 401) {
						// 			Swal.fire({
						// 				title: "Không thể xoá danh mục!",
						// 				text: data.message,
						// 				type: "error",
						// 				confirmButtonColor: "#d03f3f"
						// 			})
						// 		} else if (status == 404) {
						// 			$window.location.href = "#404";
						// 		} else if (status == 500) {
						// 			$window.location.href = "#500";
						// 		}
						// 	});
					}
				})
	}

	$scope.sortColumn = "name";
	$scope.reverseSort = false;
	$scope.currentPage = 0;
	$scope.pageSize = 10;
	$scope.pageCount = Math.ceil($scope.listCategory.length / $scope.pageSize);

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

	// $scope.first = function () {
	// 	$scope.currentPage = 0;
	// 	console.log("$scope.sortColumn" + $scope.sortColumn + "/n"
	// 		+ "$scope.reverseSort" + $scope.reverseSort + "/n"
	// 		+ "$scope.currentPage" + $scope.currentPage + "/n"
	// 		+ "$scope.pageSize" + $scope.pageSize + "/n")
	// }

	// $scope.previous = function () {
	// 	if ($scope.currentPage > 0) {
	// 		$scope.currentPage -= $scope.pageSize;
	// 	}
	// }

	// $scope.next = function () {
	// 	if ($scope.currentPage < ($scope.pageCount - 1) * $scope.pageSize) {
	// 		$scope.currentPage += $scope.pageSize;
	// 	}
	// }

	// $scope.last = function () {
	// 	$scope.currentPage = ($scope.pageCount - 1) * $scope.pageSize;
	// }

	// $scope.setPage = function (page) {
	// 	var startPage, endPage;
	// 	//var totalPages = Math.ceil(itemCount / itemPerPage);
	// 	if ($scope.pageCount <= $scope.pageSize) {
	// 		startPage = 1;
	// 		endPage = $scope.pageCount;
	// 	} else {
	// 		if ($scope.currentPage + 1 >= $scope.pageCount) {
	// 			startPage = $scope.pageCount - ($scope.pageSize - 1);
	// 			endPage = $scope.pageCount;
	// 		} else {
	// 			startPage = $scope.currentPage - parseInt($scope.pageSize / 2);
	// 			startPage = startPage <= 0 ? 1 : startPage;
	// 			endPage = (startPage + $scope.pageSize - 1) <= $scope.pageCount ? (startPage + $scope.pageSize - 1) : $scope.pageCount;
	// 			if ($scope.pageCount === endPage) {
	// 				startPage = endPage - $scope.pageSize + 1;
	// 			}
	// 		}
	// 	}
	// 	var startIndex = $scope.currentPage * $scope.pageSize;
	// 	var endIndex = startIndex + $scope.pageSize;
	// 	var index = startPage;
	// 	var pages = [];
	// 	for (; index < endPage + 1; index++)
	// 		pages.push(index);
	// 	// $scope.pager.currentPage = currentPage;
	// 	// $scope.pager.totalPages = totalPages;
	// 	// $scope.pager.startPage = startPage;
	// 	// $scope.pager.endPage = endPage;
	// 	// $scope.pager.startIndex = startIndex;
	// 	//$scope.pager.endIndex = endIndex;
	// 	$scope.pages = pages;
	// }
});
