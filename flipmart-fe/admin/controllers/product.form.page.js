app.controller('productFormCtrl', function ($scope, $rootScope, $http, $window) {
    var files = [];
    categories = [];

    $('#validate_form_product').parsley();
    $("#startDayDiscount").datepicker({
        dateFormat: 'dd/mm/yyyy'
    });
    $("#endDayDiscount").datepicker({
        dateFormat: 'dd/mm/yyyy'
    });
    $('#color').select2();

    $scope.submit = function (product) {
        if ($('#validate_form_product').parsley().isValid()) {
            let data = angular.copy(product);
            let formData = new FormData();
            for (var i in files) {
                formData.append('file', files[i]);
            }
            var colors = $("#color").val();
            formData.append('colorId', colors);
            formData.append('categoryId', categories);
            for (let key in data) {
                formData.append(key, data[key])
            }
            $rootScope.post($rootScope.createProductApi, formData)
                .success(function (data, status, headers, config) {
                    Swal.fire({
                        title: "Thông báo!",
                        text: data.message,
                        type: "success",
                        confirmButtonColor: "#348cd4"
                    }).then(function () {
                        files = [],
                            colors = [],
                            categories = [];
                        window.location.href = "#product";
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

    $scope.checkCategory = function (index) {
        $scope.listCategory[index].checked = !$scope.listCategory[index].checked;
        categories.push($scope.listCategory[index].id);
    };

    $scope.listColor = [];

    $rootScope.get($rootScope.listColorApi)
        .success(function (data, status, headers, config) {
            $scope.listColor = data.data;
        }).error(function (data, status, header, config) {
            if (status == 404) {
                $window.location.href = "#404";
            } else if (status == 500) {
                $window.location.href = "#500";
            }
        });

    $scope.listBrand = [];

    $rootScope.get($rootScope.listBrandApi)
        .success(function (data, status, headers, config) {
            $scope.listBrand = data.data;
        }).error(function (data, status, header, config) {
            if (status == 404) {
                $window.location.href = "#404";
            } else if (status == 500) {
                $window.location.href = "#500";
            }
        });

    $rootScope.get($rootScope.listCategoryApi)
        .success(function (data, status, headers, config) {
            let category = document.getElementById("category"),
                categoryDetail = document.getElementById("categoryDetail");
            for (const x of data.data) {
                category.options[category.options.length] = new Option(x.name, x.id);
            }
            category.onchange = function () {
                categoryDetail.length = 1;
                if (this.value != "") {
                    $rootScope.get($rootScope.listCategoryDetailApi + '/' + this.value)
                        .success(function (data, status, headers, config) {
                            for (const x of data.data) {
                                categoryDetail.options[categoryDetail.options.length] = new Option(x.name, x.id);
                            }
                        }).error(function (data, status, header, config) {
                            if (status == 404) {
                                $window.location.href = "#404";
                            } else if (status == 500) {
                                $window.location.href = "#500";
                            }
                        });
                }
            };
            categoryDetail.onchange = function () {
                if (this.value != "") {
                    console.log("🚀 ~ file: product.form.page.js:131 ~ this.value:", this.value)
                }
            };
        }).error(function (data, status, header, config) {
            if (status == 404) {
                $window.location.href = "#404";
            } else if (status == 500) {
                $window.location.href = "#500";
            }
        });

    var dragArea = document.querySelector('#drag-area'),
        input = document.querySelector('#drag-area input'),
        showImage = document.querySelector('#showImage');

    $scope.chooseFile = function () {
        input.click();
    }

    $scope.showImages = function () {
        $rootScope.lengthFile = files.length;
        showImage.innerHTML = files.reduce((prev, curr, index) => {
            return `${prev}
                <div class="col-sm-2 col-lg-2">
                    <button onclick="delImage(${index})" class="btn btn-danger waves-effect waves-light">
                        <i class="fas fa-ban"></i>
                    </button>
                    <div class="card">
                        <img class="card-img img-fluid" src="${URL.createObjectURL(curr)}" alt="Card image cap">
                    </div>
                </div>`
        }, '');
    }

    $scope.delImage = function (index) {
        files.splice(index, 1);
        $scope.showImages();
    }

    input.addEventListener('change', () => {
        let file = input.files;
        if (file.length == 0 || file.length > 12) return;
        for (let i = 0; i < file.length; i++) {
            if (file[i].type.split("/")[0] != 'image') continue;
            if (!files.some(e => e.name == file[i].name)) files.push(file[i])
        }
        $scope.showImages();
    });

    dragArea.addEventListener('dragover', e => {
        e.preventDefault()
        dragArea.classList.add('dragover')
    })

    dragArea.addEventListener('dragleave', e => {
        e.preventDefault()
        dragArea.classList.remove('dragover')
    });

    dragArea.addEventListener('drop', e => {
        e.preventDefault()
        dragArea.classList.remove('dragover');
        let file = e.dataTransfer.files;
        for (let i = 0; i < file.length; i++) {
            if (file[i].type.split("/")[0] != 'image') continue;
            if (!files.some(e => e.name == file[i].name)) files.push(file[i])
        }
        $scope.showImages();
    });
});

// window.Parsley.addValidator('checkDate', {
//     requirementType: 'string',
//     validateString: function (value, requirement) {
//         var startDayDiscount = (new Date(requirement)).getTime();
//         console.log("🚀 ~ file: product.form.page.js:171 ~ startDayDiscount:", startDayDiscount)
//         var endDayDiscount = (new Date(value)).getTime();
//         console.log("🚀 ~ file: product.form.page.js:173 ~ endDayDiscount:", endDayDiscount)
//         return endDayDiscount > startDayDiscount;
//     },
//     messages: {
//         vi: 'Ngày hết hạn phải lớn hơn ngày bắt đầu.'
//     }
// });