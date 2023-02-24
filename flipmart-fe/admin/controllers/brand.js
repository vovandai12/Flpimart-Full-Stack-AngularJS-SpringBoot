app.controller('brandCtrl', function ($scope, $rootScope, $http, $window) {
    $scope.listBrand = [];

    $rootScope.get($rootScope.listBrandApi).success(function (data, status, headers, config) {
        $scope.listBrand = data.data;
    }).error(function (data, status, header, config) {
        if (status == 404) {
            $window.location.href = "#404";
        } else if (status == 500) {
            $window.location.href = "#500";
        }
    });

    $("#validate_form_brand").parsley();

    $scope.submit = function (brand) {
        if ($('#validate_form_brand').parsley().isValid()) {
            let data = angular.copy(brand);
            let formData = new FormData();
            for (let key in data) {
                formData.append(key, data[key])
            }
            $rootScope.post($rootScope.createBrandApi, formData).success(function (data, status, headers, config) {
                Swal.fire({
                    title: "Thông báo!",
                    text: data.message,
                    type: "success",
                    confirmButtonColor: "#348cd4"
                }).then(function () {
                    window.location.href = "#brand";
                })
            }).error(function (data, status, header, config) {
                if (status == 401) {
                    Swal.fire({
                        title: "Không thể tạo mới!",
                        text: data.message,
                        type: "error",
                        confirmButtonColor: "#d03f3f"
                    })
                } else if (status == 404) {
                    window.location.href = "#404";
                } else if (status == 500) {
                    window.location.href = "#500";
                }
            });
        }
    }

    $scope.delete = function (data) {
        Swal.fire(
            {
                title: "Bạn có muốn xoá ?",
                text: "Bạn có muốn xoá thương hiệu " + data.name + "!",
                type: "warning",
                showCancelButton: !0,
                confirmButtonColor: "#348cd4",
                cancelButtonColor: "#6c757d",
                confirmButtonText: "Xác nhận!"
            })
            .then(
                function (t) {
                    if (t.value == true) {
                        $rootScope.delete($rootScope.deleteBrandApi, data.id)
                            .success(function (data, status, headers, config) {
                                Swal.fire("Xoá thành công!", "Đã xoá thương hiệu.", "success")
                                    .then(function () {
                                        $window.location.href = "#brand";
                                    })
                            })
                            .error(function (data, status, header, config) {
                                if (status == 401) {
                                    Swal.fire({
                                        title: "Không thể thương hiệu!",
                                        text: data.message,
                                        type: "error",
                                        confirmButtonColor: "#d03f3f"
                                    })
                                } else if (status == 404) {
                                    $window.location.href = "#404";
                                } else if (status == 500) {
                                    $window.location.href = "#500";
                                }
                            });
                    }
                })
    }
});