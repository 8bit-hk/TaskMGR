let modalTask;
let isSelect;
let count = 0;

function selectOpen(){
	isSelect=true;
	const selectExcept = document.getElementsByClassName("form-wrapper");
	
	selectExcept[0].addEventListener('click',function(){
		isSelect = false;
	})
}

function modalOpen(t){

	const modal = document.getElementById('easyModal');
	const tasks = document.getElementsByClassName("task");
	const modalText = document.getElementsByClassName("modalText")
	const modalPriority = document.getElementsByClassName("modal-priority");
	const modalStatus = document.getElementsByClassName("modal-status");
	const optionUsers =document.getElementsByClassName("option-user");
	let  Task = t[0];
	
	for(let i = 0; i< tasks.length; i++){
		tasks[i].addEventListener('click',function(){
			Task = t[i];
			
			// 優先度
			switch(Task.priority.id){
				case 1:
					modalPriority[0].checked = true;
					break;
				case 2:
					modalPriority[1].checked = true;
					break;
				case 3:
					modalPriority[2].checked = true;
					break;					
			}
			
			// タスク名
			modalText[0].value = Task.name;
			
			// 期限
			modalText[1].value = Task.dueTime;
			
			// 担当者
			if(Task.user != null){				
				for(let i = 0; i < optionUsers.length;i++){
					if(optionUsers[i].value == Task.user.id){
						optionUsers[i].selected = true;										
					}
				}
			}else{
				optionUsers[0].selected = true;	
			}

			// 状態
			switch(Task.status.id){
				case 1:
					modalStatus[0].checked = true;
					break;
				case 2:
					modalStatus[1].checked = true;
					break;
				case 3:
					modalStatus[2].checked = true;
					break;					
			}
			
			// 未割当てタスクのプルダウンリストをクリックしていた場合モーダルを表示しない
			if(!isSelect){
				modal.style.display = 'block';					
			}
			else{
				modal.style.display = 'none';	
			}
			modalTask = Task;
		},false);
	}
	
	const modalclose = document.getElementById('easyModal');
	modalclose.addEventListener('click', (e) => {
	  if(e.target.closest('.modal-content') === null) {
	    modalclose.style.display = 'none';
	  }

	})

}


// 編集ボタンがクリックされた時に入力可能にする
//buttonOpen.addEventListener('click', modalOpen);
function updateForm(){

	const modaltext = document.getElementsByClassName("modalText");
	const modalpriority = document.getElementsByClassName("modal-priority");
	const modalStatus = document.getElementsByClassName("modal-status");
	for(let i = 0; i < modaltext.length; i++){
		modaltext[i].disabled = false;
		
	}
	for(let i = 0; i < modalpriority.length; i++){
			modalpriority[i].disabled = false;	
	}
	for(let i = 0; i < modalStatus.length; i++){
			modalStatus[i].disabled = false;	
	}
	const selectedUser = document.querySelector('select[name="userId"]');
	selectedUser.disabled = false;
	
	const modalDaleteBtn = document.getElementById("execute-delete");
	modalDaleteBtn.textContent = "キャンセル";
	modalDaleteBtn.setAttribute('onclick','UpdateCancel()');
	const modalUpdateBtn = document.getElementById("update-form");
	
	modalUpdateBtn.textContent = "確定";
	modalUpdateBtn.setAttribute('onclick','updateExecute()');
	
	const modalclose = document.getElementById('easyModal');
	modalclose.addEventListener('click', (e) => {
		if(e.target.closest('.modal-content') === null) {
		    modalclose.style.display = 'none';
			modalClose();
		}
	})
}

function UpdateCancel(){
	const modaltext = document.getElementsByClassName("modalText");
	const modalpriority = document.getElementsByClassName("modal-priority");
	const modalStatus = document.getElementsByClassName("modal-status");
	for(let i = 0; i < modaltext.length; i++){
		modaltext[i].disabled = true;
	}
	for(let i = 0; i < modalpriority.length; i++){
			modalpriority[i].disabled = true;	
	}
	for(let i = 0; i < modalStatus.length; i++){
			modalStatus[i].disabled = true;	
	}
	const selectedUser = document.querySelector('select[name="userId"]');
	selectedUser.disabled = true;
	
	const modalDaleteBtn = document.getElementById("execute-delete");
	const modalUpdateBtn = document.getElementById("update-form");
	
	modalDaleteBtn.textContent = "削除";
	modalDaleteBtn.setAttribute('onclick','deleteData()');
	
	modalUpdateBtn.textContent = "編集";
	modalUpdateBtn.setAttribute('onclick','updateForm()');
}

// モーダルが閉じるときの処理
//buttonClose.addEventListener('click', modalClose);
function modalClose() {
	const modal = document.getElementById('easyModal');
	
	const modaltext = document.getElementsByClassName("modalText");
	const modalpriority = document.getElementsByClassName("modal-priority");
	const modalStatus = document.getElementsByClassName("modal-status");
	
	for(let i = 0; i < modaltext.length; i++){
		modaltext[i].disabled = true;
	}
	for(let i = 0; i < modalpriority.length; i++){
			modalpriority[i].disabled = true;	
	}
	for(let i = 0; i < modalStatus.length; i++){
		modalStatus[i].disabled = true;	
	}
	const selectedUser = document.querySelector('select[name="userId"]');
	selectedUser.disabled = true;
	
	const modalUpdateBtn = document.getElementById("update-form");
	const modalDaleteBtn = document.getElementById("execute-delete");

	modalUpdateBtn.textContent = "編集";
	modalUpdateBtn.setAttribute('onclick','updateForm()');
	modalDaleteBtn.textContent = "削除";
	modalDaleteBtn.setAttribute('onclick','deleteData()');
	modal.style.display = 'none';
}

// 編集確定
function updateExecute(){
	
	let prioritySelectedRadio = document.querySelector('input[name="modalpriority"]:checked');
	let statusSelectedRadio = document.querySelector('input[name="modalstatus"]:checked');
	let updateTask = modalTask;

	const modalText = document.getElementsByClassName("modalText")
	const selectedUser = document.querySelector('select[name="userId"]');

	// DB更新
	taskDBUpdate(updateTask.id,prioritySelectedRadio.value,modalText[0].value,
					modalText[1].value,selectedUser.value,statusSelectedRadio.value,location.pathname);
}

// 編集ボタンを押されたタイミングでDBを更新するPostをなげる
async function taskDBUpdate(UPtaskId,UPpriorityId,UPtaskName,UPdueTime,UPrepId,UPstatusID,pathName){
 	
	console.log(pathName)
	const url = "http://localhost:8080/taskMGR/task/updateDB";
	const form = new FormData();
	form.append('taskId', UPtaskId);
	form.append('priorityId', UPpriorityId);
	form.append('taskName', UPtaskName);
	form.append('dueTime', UPdueTime);
	form.append('repId', UPrepId);
	form.append('statusId', UPstatusID);
	form.append('path', pathName);
	if(location.search != null){
		form.append("search",location.search);		
	}else{
		form.append("search",null);	
	}
	const params = {
		method : "POST", 
		headers: {
				'X-CSRF-Token': getCsrfToken()
		},
		body : form
	};
	
	var res = await fetch(url, params)

	if (res.status == 200){
		alert("更新しました。");	
	}else{
		alert("更新処理中に問題が発生しました");
	}
	window.location.assign(res.url);

}

// 削除ボタンが押された時
function deleteData(){
	taskDBdelete(modalTask.id);
}

// タスク詳細モーダルから削除ボタンを押された際の処理
async function taskDBdelete(taskId){
 
	const url = "http://localhost:8080/taskMGR/task/deleteDB";
	const form = new FormData();
	form.append('taskId', taskId);
	const params = {
		method : "POST", 
		headers: {
				'X-CSRF-Token': getCsrfToken()
		},
		body : form
	};
	
	var res = await fetch(url, params)

	if (res.status == 200){
		alert("削除しました。");	
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
