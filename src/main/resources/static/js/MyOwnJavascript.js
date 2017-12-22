function testMine() {
	alert("My own javascript is loaded");
}

$(function() {
//	static
//	
//	$('#example').DataTable({
//		columnDefs : [ {
//			targets : [ 0, 1, 2 ],
//			className : 'mdl-data-table__cell--non-numeric'
//		} ]
//	});
	
	
//	ajax-source
	$('#example').DataTable( {
        "ajax": "/dandelion-api/employees",
        "columns": [
            { title: "First Name", data: "firstName" },
            { title: "Last Name", data: "lastName" },
            { title: "Status", data: "status" },
            { title: "Email", data: "email" }
        ]
    } );
});