
// plain ajax without form refresh!!!
$(document).ready(function() {
	$('#textForm').on('submit', function (event) {
		
		event.preventDefault(); //prevent default action of form submit
		
//		var post_url = $(this).attr("action"); //get form action url
//		var request_method = $(this).attr("method"); //get form GET/POST method
		var form_data = $(this).serialize(); //Encode form elements for submission
		
		$.ajax({
			url : "text",
			type: "post",
			data : form_data
		}).done(function(response){ //
			$('#ajaxSendTextServletResponse').html(response);
		});
	});
});