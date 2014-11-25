
  $(document).ready(function() { 
	  $("#create_form_button").click(function() {
		  var template_id = $("#template_id").val();
		  var template_version =$("#template_version").val(); 
		  var url = "/PROMS_PREMS/MVKServlet";
		  $("#create_form").find("#result").replaceWith("<div id=\"result\">" +
			 		"Skapar formulär...</div>");
		  
		  $.ajax({
			  url :url,
			  data:{
				  "do" : "createForm",
				  "templateID" : template_id,
				  "templateVersion" : template_version
			  },
			  success: function(data){
				 $("#create_form").find("#result").replaceWith("<div id=\"result\">" +
				 		"Resultat: "+data+"</div>");
			  }
		
		  });
	  });
	  $("#get_form_button").click(function() {
		  var form_id = $("#form_id").val(); 
		  var url = "/PROMS_PREMS/MVKServlet";
		  $("#get_form").find("#result").replaceWith("<div id=\"result\">" +
			 		"Resultat:<br/><textarea style=\"width:800px; height:800px\">Hämtar formulär...</textarea></div>");
		  
		  $.ajax({
			  url :url, 
			  accept: "xml",
			  data:{
				  "do" : "getForm",
				  "formID" : form_id,
			  },
			  success: function(data){
				  console.log(data);
				 $("#get_form").find("#result").replaceWith("<div id=\"result\">" +
				 		"Resultat:<br/><textarea style=\"width:800px; height:800px\">"+data+"</textarea></div>");
			  }
		
		  });
	  });
	  
  });