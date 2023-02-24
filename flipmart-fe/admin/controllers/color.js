app.controller('colorCtrl', function ($scope, $rootScope, $http, $window) {
    $scope.listColor = [];

    $rootScope.get($rootScope.listColorApi)
        .success(function (data, status, headers, config) {
            $scope.listColor = data.data;
        })
        .error(function (data, status, header, config) {
            if (status == 404) {
                $window.location.href = "#404";
            } else if (status == 500) {
                $window.location.href = "#500";
            }
        });

    $("#validate_form_color").parsley();

    $scope.submit = function (color) {
        if ($('#validate_form_color').parsley().isValid()) {
            let data = angular.copy(color);
            let formData = new FormData();
            for (let key in data) {
                formData.append(key, data[key])
            }
            $rootScope.post($rootScope.createColorApi, formData)
                .success(function (data, status, headers, config) {
                    Swal.fire({
                        title: "Thông báo!",
                        text: data.message,
                        type: "success",
                        confirmButtonColor: "#348cd4"
                    }).then(function () {
                        window.location.href = "#color";
                    })
                })
                .error(function (data, status, header, config) {
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
                text: "Bạn có muốn xoá màu " + data.name + "!",
                type: "warning",
                showCancelButton: !0,
                confirmButtonColor: "#348cd4",
                cancelButtonColor: "#6c757d",
                confirmButtonText: "Xác nhận!"
            })
            .then(
                function (t) {
                    if (t.value == true) {
                        $rootScope.delete($rootScope.deleteColorApi, data.id)
                            .success(function (data, status, headers, config) {
                                Swal.fire("Xoá thành công!", "Đã xoá màu sắc.", "success")
                                    .then(function () {
                                        $window.location.href = "#color";
                                    })
                            })
                            .error(function (data, status, header, config) {
                                if (status == 401) {
                                    Swal.fire({
                                        title: "Không thể xoá màu sắc!",
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