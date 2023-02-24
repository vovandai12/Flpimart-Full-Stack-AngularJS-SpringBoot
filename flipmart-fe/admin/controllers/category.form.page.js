app.controller('categoryFormCtrl', function ($scope, $rootScope, $http, $window) {
	$('#validate_form_category').parsley();
	$('#validate_form_category_detail').parsley();

	$scope.addCategoryDetail = function () {
		new Custombox.modal({
			content: {
				target: '#addCategoryDetail',
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
	}

	var input = 0;

	$scope.addCategoryDetailForm = function () {
		input++;
		$('#newInput').append(`
			<div id="rowCategory" class="form-group row">
				<label for="categoryDetail" class="col-md-4 col-form-label"> Thuộc tính` + input + `
					<span class="text-danger">*</span>
				</label>
				<div class="col-md-6">
					<input type="text" id="input` + input + `" required class="form-control" placeholder="Thuộc tính...">
				</div>
				<div class="col-md-1">
                    <button onclick="deleteRow(this)" class="btn btn-icon waves-effect btn-danger"> <span>&times;</span></button>
                </div>
			</div>`);
	}

	var categoryDetail = [];

	$scope.submitCategoryDetail = function () {
		if ($('#validate_form_category_detail').parsley().isValid()) {
			categoryDetail = [];
			for (let index = 0; index <= input; index++) {
				var text = document.getElementById('input' + index);
				var value = text ? text.value : '';
				if (value != '')
					categoryDetail.push(value);
			}
		}
		$('#showCategoryDetail').empty();
		categoryDetail.forEach(element => {
			var html = `<li>` + element + `</li>`;
			$('#showCategoryDetail').append(html);
		});
		Custombox.modal.close();
	}

	$scope.submit = function (category) {
		if ($('#validate_form_category').parsley().isValid()) {
			let data = angular.copy(category);
			let formData = new FormData();
			for (let key in data) {
				formData.append(key, data[key])
			}
			formData.append('array', categoryDetail);
			$rootScope.post($rootScope.createCategoryApi, formData)
				.success(function (data, status, headers, config) {
					Swal.fire({
						title: 'Thông báo!',
						text: data.message,
						type: 'success',
						confirmButtonColor: '#348cd4'
					}).then(function () {
						window.location.href = '#category';
					})
				}).error(function (data, status, header, config) {
					if (status == 401) {
						Swal.fire({
							title: 'Không thể tạo mới!',
							text: data.message,
							type: 'error',
							confirmButtonColor: '#d03f3f'
						})
					} else if (status == 404) {
						window.location.href = '#404';
					} else if (status == 500) {
						window.location.href = '#500';
					}
				});
		}
	}

});

function deleteRow(e) {
	$(e).parents('#rowCategory').remove();
}