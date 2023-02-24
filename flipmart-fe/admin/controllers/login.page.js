app.controller('loginCtrl', function ($scope, $rootScope, $http, $window, $localStorage) {
	$('#validate_form_login').parsley();

	$scope.submit = function (user) {
		if ($('#validate_form_login').parsley().isValid()) {
			let data = angular.copy(user);
			$rootScope.login($rootScope.loginApi, data)
				.success(function (data, status, headers, config) {
					$window.sessionStorage.token = data.token;
					//localStorage.setItem("token", JSON.stringify(data.token));
					localStorage.setItem("userLogin", JSON.stringify(data.user));
					Swal.fire({
						title: "Thông báo!",
						text: data.message,
						type: "success",
						confirmButtonColor: "#348cd4"
					}).then(function () {
						$window.location.reload();
					})
				})
				.error(function (data, status, header, config) {
					delete $window.sessionStorage.token;
					if (status == 401) {
						Swal.fire({
							title: "Không thể đăng nhập!",
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
	}
});

window.Parsley.addValidator('uppercase', {
	requirementType: 'number',
	validateString: function (value, requirement) {
		let uppercases = value.match(/[A-Z]/g) || [];
		return uppercases.length >= requirement;
	},
	messages: {
		vi: 'Mật khẩu của bạn phải chứa ít nhất (%s) chữ hoa.'
	}
});

window.Parsley.addValidator('lowercase', {
	requirementType: 'number',
	validateString: function (value, requirement) {
		let lowecases = value.match(/[a-z]/g) || [];
		return lowecases.length >= requirement;
	},
	messages: {
		vi: 'Mật khẩu của bạn phải chứa ít nhất (%s) chữ thường.'
	}
});

window.Parsley.addValidator('number', {
	requirementType: 'number',
	validateString: function (value, requirement) {
		let numbers = value.match(/[0-9]/g) || [];
		return numbers.length >= requirement;
	},
	messages: {
		vi: 'Mật khẩu của bạn phải chứa ít nhất (%s) số.'
	}
});

window.Parsley.addValidator('special', {
	requirementType: 'number',
	validateString: function (value, requirement) {
		let specials = value.match(/[^a-zA-Z0-9]/g) || [];
		return specials.length >= requirement;
	},
	messages: {
		vi: 'Mật khẩu của bạn phải chứa ít nhất (%s) ký tự đặc biệt.'
	}
});