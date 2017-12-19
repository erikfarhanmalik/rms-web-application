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
			    data: JSON.stringify(data),
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
	
	$.serializeFormToJson = function (form) {
		var obj = {};
		var elements = $(form).find("input, select, textarea");
		for (var i = 0; i < elements.length; ++i) {
			var element = elements[i];
			var name = element.name;
			var value = element.value;
			if (name) {
				if (name.indexOf(".id") === -1) {
					obj[name] = value;
				} else {
					var names = name.split(".");
					var childObject = {};
					childObject["id"] = value;
					obj[names[0]] = childObject;
				}
			}
		}
		return obj;
	}

	$.deserializeJsonStringToForm = function (jsonData, form) {
		for ( var key in jsonData) {
			if(jsonData[key] !== null){
				if (typeof jsonData[key] === 'object') {
					$(form).find('[name="' + key + '.id"]').val(jsonData[key].id);
				} else {
					$(form).find('[name="' + key + '"]').val(jsonData[key]);
				}	
			}
		}
	}
});

