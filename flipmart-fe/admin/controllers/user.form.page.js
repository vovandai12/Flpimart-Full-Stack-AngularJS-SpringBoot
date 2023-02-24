app.controller('userFormCtrl', function ($scope, $rootScope, $http, $window) {
	$('#validate_form_user').parsley();
	$("#birthday").datepicker({
		dateFormat: 'dd/mm/yyyy'
	});

	$scope.submit = function (user) {
		if ($('#validate_form_user').parsley().isValid()) {
			let data = angular.copy(user);
			let formData = new FormData()
			formData.append('file', $scope.file);
			formData.append('address', $scope.address);
			for (let key in data) {
				formData.append(key, data[key])
			}
			$rootScope.post($rootScope.createUserApi, formData)
				.success(function (data, status, headers, config) {
					Swal.fire({
						title: "Thông báo!",
						text: data.message,
						type: "success",
						confirmButtonColor: "#348cd4"
					}).then(function () {
						window.location.href = "#user";
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

	$scope.readURL = function (e) {
		if (e.target.files && e.target.files[0]) {
			$scope.file = e.target.files[0];
			let reader = new FileReader();
			reader.onload = function (e) {
				$('#blah')
					.attr('src', e.target.result);
			};
			reader.readAsDataURL(e.target.files[0]);
		}
	}

	$http.get("../../assets/admin/js/pages/DiaGioiHanhChinhVN.js").then(function (response) {
		let citis = document.getElementById("city"),
			districts = document.getElementById("district"),
			wards = document.getElementById("ward"),
			address = '';
		for (const x of response.data) {
			citis.options[citis.options.length] = new Option(x.Name, x.Id);
		}
		citis.onchange = function () {
			district.length = 1;
			ward.length = 1;
			if (this.value != "") {
				const result = response.data.filter(n => n.Id === this.value);

				for (const k of result[0].Districts) {
					district.options[district.options.length] = new Option(k.Name, k.Id);
				}
			}
		};
		district.onchange = function () {
			ward.length = 1;
			const dataCity = response.data.filter((n) => n.Id === citis.value);
			if (this.value != "") {
				const dataWards = dataCity[0].Districts.filter(n => n.Id === this.value)[0].Wards;

				for (const w of dataWards) {
					wards.options[wards.options.length] = new Option(w.Name, w.Id);
				}
			}
		};
		wards.onchange = function () {
			address = wards.options[wards.selectedIndex].text + ', '
				+ districts.options[districts.selectedIndex].text + ', ' + citis.options[citis.selectedIndex].text;
			$scope.address = address;
		};
	});
});

window.Parsley.addValidator('checkMinAge', {
	requirementType: 'number',
	validateString: function (value, requirement) {
		var dateNow = (new Date()).getFullYear();
		var birthDay = (new Date(value)).getFullYear();
		return dateNow - birthDay >= requirement;
	},
	messages: {
		vi: 'Tuổi phải lớn hơn hoặc bằng %s.'
	}
});

window.Parsley.addValidator('checkMaxAge', {
	requirementType: 'number',
	validateString: function (value, requirement) {
		var dateNow = (new Date()).getFullYear();
		var birthDay = (new Date(value)).getFullYear();
		return dateNow - birthDay <= requirement;
	},
	messages: {
		vi: 'Tuổi phải nhỏ hơn hoặc bằng %s.'
	}
});
