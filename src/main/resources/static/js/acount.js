

function deleteBtnClick(){
	const radioBtn = document.getElementsByClassName("delete-user");

	for(let i = 0;i<radioBtn.length;i++){
		if(radioBtn[i].checked){
			acountDeleteDB(radioBtn[i].value);
		}
	}
	
}

async function acountDeleteDB(userId){
 
	const url = "http://localhost:8080/admin/deleteExecute";
	const form = new FormData();
	form.append('userId', userId);
	const params = {
		method : "POST", 
		headers: {
				'X-CSRF-Token': getCsrfToken()
		},
		body : form
	};
	
	var res = await fetch(url, params)

	if (res.status == 200){
		alert("更新しました。一覧トップに戻ります。");	
	}else{
		alert("更新処理中に問題が発生しました");
	}
	window.location.assign(res.url);

}



 function getCsrfToken(){
 // jsからfetchでリクエストを送る場合はcsrfトークンを手動で持ってくる必要がある
var csrfElm = document.querySelector('input[name="_csrf"]');
console.log(csrfElm)
var csrf_token = csrfElm.value;
return csrf_token;
}


