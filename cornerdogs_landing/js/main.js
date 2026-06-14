/*
 * Landing page support JavaScript.
 * @author joseph.burris@gmail.com (place-holder)
 */

let popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
let popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
	return new bootstrap.Popover(popoverTriggerEl)
});

$('textarea').each(function() {
	$(this).attr('maxlength', '2000');
	countChars($(this));
});

$('textarea').keyup(function () {
	countChars($(this));
});

function countChars(item){
	let id = item.attr('id');
	let max = 2000;
	if (item.val().length > max) {
		item.val(item.val().substr(0, max));
	}
	$('#'+id+'CharsRemaining').html('You have ' + (max - item.val().length) + ' characters remaining.');
}

function send(){
	let uid=document.querySelector('input[name=uid]').value;
	var address=document.querySelector('#address');
	var message=document.querySelector('#message');
	let url='http://localhost:9091/add?uid='+uid+'&address='+address.value+'&message='+message.value;
	let req=window.XMLHttpRequest?new XMLHttpRequest():new ActiveXObject('Microsoft.XMLHTTP');
	if(req){
		req.onreadystatechange=function(){
			let ready=req.readyState;
			let data=null;
			if(ready==4){
				data='success'===req.responseText?
					'Thank you for contacting us! We will review your message as soon as possible!'
					:'Oops! An error occurred. Please bear with us as address the issue.';
				let modal = $('#response');
				modal.find('.response-text').html(data);
				modal.modal('show');
				modal.on('hidden.bs.modal',function(){
					address.value='';
					message.value='';
					countChars($('textarea'));
				});
			}
		};
		try {
			req.open('GET',url,true);
		} catch (e) {
			let modal = $('#response');
			modal.find('.response-text').html('Oops! An error occurred. Please bear with us as address the issue.');
			modal.modal('show');
		}
		req.setRequestHeader('Access-Control-Allow-Origin', '*');
		//req.setRequestHeader('Content-Type','application/json;charset=utf-8');
		req.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
		req.send(null);
	}
	return false;
}
