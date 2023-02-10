function vaildCheck(){
	$('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })    
    })
}
function back_vaild(val){
	if(val=="id"){
		$('.fieldError__id').hide();
	}
	if(val=="id"){
		$('.fieldError__name').hide();
	}
	if(val=="pwd"){
		$('.fieldError__pwd').hide();
		$('.fieldError__pwdc').hide();
	}
	if(val=="Login_id"){
		$('.fieldError__Login-id').hide();
	}
	if(val=="Login_pwd"){
		$('.fieldError__Login-pwd').hide();
	}
}

function frm_smt(val){
	if(val='login'){
		if($('#id').val()==""){
			$('.fieldError__Login-id').show()
			$('#id').focus();
			return false;
		}	
		if($('#password').val()==""){
			$('.fieldError__Login-pwd').show()
			$('#password').focus();
			return false;
		}	
		
		if ($("#id_save").is(":checked")) {
			var userInputId = $("#id").val();
			setCookie("userInputId", userInputId, 60);
			setCookie("setCookieYN", "Y", 60);
		} else {
			deleteCookie("userInputId");
			deleteCookie("setCookieYN");
		}
		
		
		
		$('#frm').submit();	
	}
	else{
		$('#frm').submit();
	}
}

function CheckPwd(){
	console.log($('#password').val());
	console.log($('#password_check').val());
	
	if($('#password').val()==$('#password_check').val()){
		$('#check_Y').show();
		$('#check_N').hide();
		$('#passwordcheck').val('Y')
	}
	else{
		$('#check_N').show();
		$('#check_Y').hide();
		$('#passwordcheck').val('')
	}
}