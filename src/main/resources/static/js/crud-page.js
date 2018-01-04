$(function() {
	$('.list-button').click(function(){
		console.log("list page activated");
	});
	
	$('.form-button').click(function(){
		console.log("form page activated");
	});
	
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
});