
/////////////////////////////////////////////////////////////////////////////////////////
// ユーザ編集用メソッド																	   //
/////////////////////////////////////////////////////////////////////////////////////////

let emailOld = null;
// ユーザ情報から編集ボタンが押されたときの処理
function userEditOn(){
	const userEditText = document.getElementsByClassName("userEdit-text");
	const editOnBtnDiv = document.getElementById("userEditOnBtn-wrapper");
	const editBtnDiv = document.getElementById("userEditBtn-wrapper");
	emailOld = userEditText[2].value;
	// テキストボックスを入力可能にする
	for(let i = 0; i < userEditText.length; i++){
		userEditText[i].disabled = false;
	}
	// 編集ボタンを切り替える
	editOnBtnDiv.style.display = 'none';
	editBtnDiv.style.display = 'flex';
}

// ユーザ情報編集時にキャンセルボタンを押されたときの処理
function userEditCancel(){
	const userEditText = document.getElementsByClassName("userEdit-text");
	const editOnBtnDiv = document.getElementById("userEditOnBtn-wrapper");
	const editBtnDiv = document.getElementById("userEditBtn-wrapper");
	
	// テキストボックスを入力可能にする
	for(let i = 0; i < userEditText.length; i++){
		userEditText[i].disabled = true;
	}
	// 編集ボタンを切り替える
	editBtnDiv.style.display = 'none';
	editOnBtnDiv.style.display = 'block';
}

// ユーザ情報編集時に確定ボタンを押されたときの処理
function userEditExecute(){
	const userEditText = document.getElementsByClassName("userEdit-text");
	
	for(let i=0;i<userEditText.length;i++){
		console.log(userEditText[i].value);
	}
	
	userUpdateDB(userEditText[0].value,userEditText[1].value,userEditText[2].value,userEditText[3].value,userEditText[4].value,emailOld);
	
}
async function userUpdateDB(id, name, email, password,roleId,emailOld){
	
	const url = "http://localhost:8080/taskMGR/user/updateExecute";
	const form = new FormData();
	form.append('id', id);
	form.append('name', name);
	form.append('email', email);
	form.append('password', password);
	form.append('roleId', roleId);
	form.append('emailOld', emailOld);
	const params = {
		method : "POST", 
		headers: {
				'X-CSRF-Token': getCsrfToken()
		},
		body : form
	};
	
	var res = await fetch(url, params)

	if (res.status == 200){
		if(emailOld　== email){
			alert("更新しました。マイページに戻ります。");	
		}else{
			alert("メールアドレスが更新されました。再度ログインしてください。");
		}
	}else{
		alert("更新処理中に問題が発生しました");
	}
	window.location.assign(res.url);
}

/////////////////////////////////////////////////////////////////////////////////////////
// アカウント削除用メソッド																	   //
/////////////////////////////////////////////////////////////////////////////////////////
function deleteBtnClick(){
	const radioBtn = document.getElementsByClassName("delete-user");

	for(let i = 0;i<radioBtn.length;i++){
		if(radioBtn[i].checked){
			acountDeleteDB(radioBtn[i].value);
		}
	}
}

async function acountDeleteDB(userId){
	
	const url = "http://localhost:8080/taskMGR/user/deleteExecute";
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
		alert("ユーザを削除しました。");	
	}else{
		alert("更新処理中に問題が発生しました");
	}
	window.location.assign(res.url);
}

function getCsrfToken(){
	// jsからfetchでリクエストを送る場合はcsrfトークンを手動で持ってくる必要がある
	var csrfElm = document.querySelector('input[name="_csrf"]');
	var csrf_token = csrfElm.value;
	return csrf_token;
}


