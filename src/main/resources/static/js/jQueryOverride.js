$(function() {
	$.fn.serializeObject = function() {
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			if (o[this.name]) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	};

	$.postJSON = function(url, data, success, error) {
		return jQuery.ajax({
			Accept : "application/json; charset=utf-8",
			'type' : 'POST',
			'url' : url,
			'contentType' : 'application/json',
			'data' : JSON.stringify(data),
			'dataType' : 'json',
			'success' : success,
			'error' : error
		});
	};
	
	$.put = function(url, data, callback, type){
		 
			  if ( $.isFunction(data) ){
			    type = type || callback,
			    callback = data,
			    data = {}
			  }
			 
			  return $.ajax({
			    url: url,
			    type: 'PUT',
			    success: callback,
			    data: data,
			    contentType: type
			  });
			}
	
	$.delete = function(url, data, callback, type){
		 
			  if ( $.isFunction(data) ){
			    type = type || callback,
			        callback = data,
			        data = {}
			  }
			 
			  return $.ajax({
			    url: url,
			    type: 'DELETE',
			    success: callback,
			    data: data,
			    contentType: type
			  });
			}
});

