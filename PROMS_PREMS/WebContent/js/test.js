
  $(document).ready(function() { 

	  var url = "/PROMS_PREMS/MVKServlet";
	  $("#create_form_button").click(function() {
		  var template_id = $("#template_id").val();
		  var template_version =$("#template_version").val(); 
		 
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
		  var form_id = $("#get_form").find("#form_id").val(); 
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
				 		"Resultat:<br/><textarea style=\"width:800px; height:200px\">"+data+"</textarea></div>");
			  }
		
		  });
	  });
	  $("#answer_form_button").click(function() {
		  
		  var form_id = $("#answer_form").find("#form_id").val();
		  var form_page = $("#answer_form").find("#form_page").val();
		  var form_answers = $("#answer_form").find("#formpage_answers").val();
		  $("#answer_form").find("#result").replaceWith("<div id=\"result\">" +
			 		"Resultat:<br/><textarea style=\"width:800px; height:200px\">Svarar formulär " + 
			 		form_id + ", sida "+form_page+"</textarea></div>");
		  
		  $.ajax({
			  url :url, 
			  accept: "xml",
			  type: "POST",
			  data:{
				  "do" : "answerForm",
				  "formID" : form_id,
				  "formPage" : form_page,
				  "formAnswers": form_answers
			  },
			  success: function(data){
				  console.log(data);
				 $("#answer_form").find("#result").replaceWith("<div id=\"result\">" +
				 		"Resultat:<br/><textarea style=\"width:800px; height:200px\">"+data+"</textarea></div>");
			  }
		
		  });
	  });
	  $("#complete_form_button").click(function() {
		  var form_id = $("#complete_form").find("#form_id").val(); 
		  $("#complete_form").find("#result").replaceWith("<div id=\"result\">" +
	 		"Avslutar formulär...</div>");
		  
		  $.ajax({
			  url :url, 
			  accept: "xml",
			  data:{
				  "do" : "completeForm",
				  "formID" : form_id,
			  },
			  success: function(data){
				  console.log(data);
				 $("#get_form").find("#result").replaceWith("<div id=\"result\">" +
					 		"Resultat: "+data+"</div>");
				 }
		
		  });
	  });
  });