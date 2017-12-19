var employeeId;

$(function() {
	initiallizeMaterializeCss();
	setFormValidationRules();
	initializeControl()
});

function deleteEmployee(deleteButton){
	var id = $(deleteButton).parent('.list-action').siblings('.employeeId').val();
	$.delete("/employees/" + id, function(){
		toastr.success('Data deleted successfully');
	});
	
	$(deleteButton).parents('.employee').remove();
}

function initializeControl(){
	$('.main-control span').click(function(){
		changePage($(this).data('page-name'));
	});
}

function filterList(event) {
	if (event.keyCode == 13) {
		var keyword = $('#keyword-filter').val();
		$('#employee-list').load('employees/search?page=1&keyword=' + encodeURIComponent(keyword), function(response, status, xhr) {
			if (status == "error") {
				toastr.error('Failed to load employee data');
			}
		});    
    }
}

function movePagination(){
	var page = $('#goToPage').val(); 
	var keyword = $('#keyword-filter').val(); 
	
	if(isNaN(page)){
		toastr.error('Page should be number');
	} else {			
		$('#employee-list').load('employees/search?page=' + page + '&keyword=' + keyword, function(response, status, xhr) {
			if (status == "error") {
				toastr.error('Failed to load employee data');
			}
		});
	}
}

function changePage(page) {
	$('.page.active').removeClass('active');
	$('.' + page).addClass('active');
	
	$('.main-control span.active').removeClass('active');
	$('[data-page-name="' + page + '"]').addClass('active');
}

function selectEmployee() {
	$('.employee').click( function() {
		employeeId = $(this).find('.employeeId').val();
		
		$('.detail-page').load("/employees/" + employeeId + " #detail-content",
			function(response, status, xhr) {
				if (status == "error") {
					toastr.error('Failed to load employee data')
				} else {
					changePage('detail-page');
				}
			});
	});
}

function selectEmployeeForUpdate(updateButton) {
	employeeId = $(updateButton).parent('.list-action').siblings('.employeeId').val();
	$.get("/api/employees/" + employeeId, function(data) {
		$.deserializeJsonStringToForm(data, $('#employee-form'));
		Materialize.updateTextFields();
		$('#update-button').removeClass('hide');
		$('#submit-button').addClass('hide');
		changePage('create-page');
	});
}

function updateEmployee() {
	if ($("#employee-form").valid()) {
		var data = $.serializeFormToJson($('#employee-form'));
		data.id = employeeId;
		$.postJSON($("#employee-form").attr('action'), data, 
		function(jsonResponse, textStatus, xhr) {
			toastr.success('Employee data updated successfully')
		}, function() {
			toastr.error('Failed to update employee data')
		});
	}
}

function initiallizeMaterializeCss() {
	$('.datepicker').pickadate({
		selectMonths : true, // Creates a dropdown to control month
		selectYears : 15, // Creates a dropdown of 15 years to control year,
		today : 'Today',
		clear : 'Clear',
		close : 'Ok',
		closeOnSelect : true
	});

	$('select').material_select();
	$('select:required').on('change', function(e) {
		$("#employee-form").valid();
	});

}

function setFormValidationRules() {
	$("#employee-form").validate({
		ignore : [],
		rules : {
			firstName : {
				required : true,
				minlength : 3
			},
			status : {
				required : true
			},
			gender : {
				required : true
			},
			hiredDate : {
				required : true
			},
			grade : {
				required : true
			},
			division : {
				required : true
			},
			location : {
				required : true
			},
			phone : {
				required : true
			},
			username : {
				required : true
			},
			email : {
				required : true,
				email : true
			}
		},
		errorElement : 'div',
		errorPlacement : function(error, element) {
			var placement = $(element).data('error');
			if (placement) {
				$('.' + placement).append(error)
			} else {
				error.insertAfter(element);
			}
		}
	});
}

function addEmployeeListEntry(employee) {
	var result = '<div class="employee">' + '<div class="picture">'
			+ '<img src="/img/user.png" />' + '</div>' + '<span class="name">'
			+ employee.firstName + ' ' + employee.lastName + '</span>'
			+ '<span class="grade">' + employee.division.code + ' - '
			+ employee.grade.code + '</span>' + '<span class="contact">'
			+ employee.location + ', ' + employee.phone + '</span>' + '</div>';

	$('.list-control').after(result);
}

function submitForm() {
	if ($("#employee-form").valid()) {
		var data = $.serializeFormToJson($('#employee-form'));
		$.postJSON($("#employee-form").attr('action'), data, function(
				jsonResponse, textStatus, xhr) {
			$('#employee-form')[0].reset();
			Materialize.updateTextFields();
			addEmployeeListEntry(jsonResponse);
			toastr.success('Employee data inserted successfully')
		}, function() {
			toastr.error('Failed to insert employee data')
		});
	}

}
