app.controller('productListCtrl', function ($scope, $rootScope, $http, $window) {
    $scope.listProduct = [];

	$rootScope.get($rootScope.listProductApi)
    .success(function (data, status, headers, config) {
		$scope.listProduct = data.data;
	}).error(function (data, status, header, config) {
		if (status == 404) {
			$window.location.href = "#404";
		} else if (status == 500) {
			$window.location.href = "#500";
		}
	});

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
	// 	$scope.userView = data;
	// 	//Địa chỉ đặt hàng
	// }

	// $scope.edit = function (data) {
	// 	new Custombox.modal({
	// 		content: {
	// 			target: '#editModal',
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
	// 	$scope.userEdit = {
	// 		"id": data.id,
	// 		"fullName": data.fullName,
	// 		"gender": data.gender,
	// 		"login": data.login,
	// 		"file": ""
	// 	};
	// }

	// $scope.delete = function (data) {
	// 	Swal.fire(
	// 		{
	// 			title: "Bạn có muốn xoá ?",
	// 			text: "Bạn có muốn xoá người dùng " + data.fullName + "!",
	// 			type: "warning",
	// 			showCancelButton: !0,
	// 			confirmButtonColor: "#348cd4",
	// 			cancelButtonColor: "#6c757d",
	// 			confirmButtonText: "Xác nhận!"
	// 		})
	// 		.then(
	// 			function (t) {
	// 				if (t.value == true) {
	// 					$http.delete($rootScope.baseApi + $rootScope.deleteUserApi + data.id, $rootScope.configDeleteToken)
	// 						.success(function (data, status, headers, config) {
	// 							Swal.fire("Xoá thành công!", "Đã xoá người dùng.", "success")
	// 								.then(function () {
	// 									$window.location.href = "#user";
	// 								})
	// 						})
	// 						.error(function (data, status, header, config) {
	// 							if (status == 401) {
	// 								Swal.fire({
	// 									title: "Không thể xoá người dùng!",
	// 									text: data.message,
	// 									type: "error",
	// 									confirmButtonColor: "#d03f3f"
	// 								})
	// 							} else if (status == 404) {
	// 								$window.location.href = "#404";
	// 							} else if (status == 500) {
	// 								$window.location.href = "#500";
	// 							}
	// 						});
	// 				}
	// 			})
	// }

	// $('#validate_form_edit_user').parsley();

	// $scope.submit = function (userEdit) {
	// 	if ($('#validate_form_edit_user').parsley().isValid()) {
	// 		let data = angular.copy(userEdit);
	// 		let formData = new FormData()
	// 		formData.append('file', $scope.file);
	// 		for (let key in data) {
	// 			formData.append(key, data[key])
	// 		}
	// 		$http.post($rootScope.baseApi + $rootScope.editUserApi, formData, $rootScope.configPostToken)
	// 			.success(function (data, status, headers, config) {
	// 				Custombox.modal.close();
	// 				Swal.fire({
	// 					title: "Thông báo!",
	// 					text: data.message,
	// 					type: "success",
	// 					confirmButtonColor: "#348cd4"
	// 				}).then(function () {
	// 					$window.location.href = "#user";
	// 				})
	// 			})
	// 			.error(function (data, status, header, config) {
	// 				Custombox.modal.close();
	// 				if (status == 401) {
	// 					Swal.fire({
	// 						title: "Không thể cập nhật!",
	// 						text: data.message,
	// 						type: "error",
	// 						confirmButtonColor: "#d03f3f"
	// 					})
	// 				} else if (status == 404) {
	// 					$window.location.href = "#404";
	// 				} else if (status == 500) {
	// 					$window.location.href = "#500";
	// 				}
	// 			});
	// 	}
	// }

	// $scope.readURL = function (e) {
	// 	if (e.target.files && e.target.files[0]) {
	// 		$scope.file = e.target.files[0];
	// 		let reader = new FileReader();
	// 		reader.onload = function (e) {
	// 			$('#blah')
	// 				.attr('src', e.target.result);
	// 		};
	// 		reader.readAsDataURL(e.target.files[0]);
	// 	}
	// }

	$scope.sortColumn = "fullName";
	$scope.reverseSort = false;
	$scope.currentPage = 0;
	$scope.pageSize = 10;
	$scope.pageCount = Math.ceil($scope.listProduct.length / $scope.pageSize);

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

	$scope.first = function () {
		$scope.currentPage = 0;
		console.log("$scope.sortColumn" + $scope.sortColumn + "/n"
			+ "$scope.reverseSort" + $scope.reverseSort + "/n"
			+ "$scope.currentPage" + $scope.currentPage + "/n"
			+ "$scope.pageSize" + $scope.pageSize + "/n")
	}

	$scope.previous = function () {
		if ($scope.currentPage > 0) {
			$scope.currentPage -= $scope.pageSize;
		}
	}

	$scope.next = function () {
		if ($scope.currentPage < ($scope.pageCount - 1) * $scope.pageSize) {
			$scope.currentPage += $scope.pageSize;
		}
	}

	$scope.last = function () {
		$scope.currentPage = ($scope.pageCount - 1) * $scope.pageSize;
	}

	$scope.setPage = function (page) {
		var startPage, endPage;
		//var totalPages = Math.ceil(itemCount / itemPerPage);
		if ($scope.pageCount <= $scope.pageSize) {
			startPage = 1;
			endPage = $scope.pageCount;
		} else {
			if ($scope.currentPage + 1 >= $scope.pageCount) {
				startPage = $scope.pageCount - ($scope.pageSize - 1);
				endPage = $scope.pageCount;
			} else {
				startPage = $scope.currentPage - parseInt($scope.pageSize / 2);
				startPage = startPage <= 0 ? 1 : startPage;
				endPage = (startPage + $scope.pageSize - 1) <= $scope.pageCount ? (startPage + $scope.pageSize - 1) : $scope.pageCount;
				if ($scope.pageCount === endPage) {
					startPage = endPage - $scope.pageSize + 1;
				}
			}
		}
		var startIndex = $scope.currentPage * $scope.pageSize;
		var endIndex = startIndex + $scope.pageSize;
		var index = startPage;
		var pages = [];
		for (; index < endPage + 1; index++)
			pages.push(index);
		// $scope.pager.currentPage = currentPage;
		// $scope.pager.totalPages = totalPages;
		// $scope.pager.startPage = startPage;
		// $scope.pager.endPage = endPage;
		// $scope.pager.startIndex = startIndex;
		//$scope.pager.endIndex = endIndex;
		$scope.pages = pages;
	}
});