'use strict'
{
	window.onload =  function(){
		if(taskList != null){
			taskListView(taskList);		
		}
	}
}



// タクス状態別チェックボックス押下時の処理
function progressCheck(){
	
	const checkboxes = document.getElementsByClassName("is-progress");
			
	const tasks = taskList;
	let tl = new Array();
	
	// チェックボックスのチェック判定
	// true=o　false=x
	if(checkboxes[0].checked && checkboxes[1].checked && checkboxes[2].checked){
		// 未着手o/進行中o/完了済o
		tl = tasks;
	}else if(!checkboxes[0].checked && checkboxes[1].checked && checkboxes[2].checked){
		// 未着手x/進行中o/完了済o
		tasks.forEach(function(task){
			if(task.status.id == 2 || task.status.id == 3){
				tl.push(task);
			}
		})
	}else if(checkboxes[0].checked && !checkboxes[1].checked && checkboxes[2].checked){
		// 未着手o/進行中x/完了済o
		tasks.forEach(function(task){
			if(task.status.id == 1 || task.status.id == 3){
				tl.push(task);
			}
		})
	}else if(checkboxes[0].checked && checkboxes[1].checked && !checkboxes[2].checked){
		// 未着手o/進行中o/完了済x
		tasks.forEach(function(task){
			if(task.status.id == 1 || task.status.id == 2){
				tl.push(task);
			}
		})
	}else if(!checkboxes[0].checked && !checkboxes[1].checked && checkboxes[2].checked){
		// 未着手x/進行中x/完了済o
		tasks.forEach(function(task){
			if(task.status.id == 3){
				tl.push(task);
			}
		})
	}else if(!checkboxes[0].checked && checkboxes[1].checked && !checkboxes[2].checked){
		// 未着手x/進行中o/完了済x
		tasks.forEach(function(task){
			if(task.status.id == 2){
				tl.push(task);
			}
		})
	}else if(checkboxes[0].checked && !checkboxes[1].checked && !checkboxes[2].checked){
		// 未着手o/進行中x/完了済x
		tasks.forEach(function(task){
			if(task.status.id == 1){
				tl.push(task);
			}
		})	
	}else{
		tl = tasks;
	}
		taskListView(tl);
}

function taskListView(t){
		/**
		 * 要素の取得
		 */	
		const ul = document.getElementsByClassName("taskList");
		const prev = document.getElementById("prev");
		const next = document.getElementById("next");
		const active = document.getElementsByClassName("active");
		const loginUser = loginUserId;

		/**
		 * ページネーション用のデータ配列
		 */		
		const　data = t;
		/**
		 * 1ページあたりの表示数
		 */
		const itemsPerPage = 10;

		/**
		 * ページネーションボタンをクリック時のdisabledの指定
		 */
		const disabledButton = () => {
			/**
			 * 最初のページネーションのボタンがactiveの場合は「<」をdisabledに
			 */
			const firstPageButton = document.querySelector("#paginateButton button:first-child");
	  		firstPageButton.classList.contains("active") ? (prev.disabled = true) : (prev.disabled = false);
	  		/**
	   		* 最後のページネーションのボタンがactiveの場合は「>」をdisabledに
	   		*/
	  		const lastPageButton = document.querySelector("#paginateButton button:last-child");
	  		lastPageButton.classList.contains("active") ? (next.disabled = true) : (next.disabled = false);
		};

		/**
		 * データ表示の関数
		 */
		const displayData = (page) => {
			
			const startData = (page - 1) * itemsPerPage;
			const endData = startData + itemsPerPage;
			const paginatedData = data.slice(startData, endData);

		  	ul[0].innerHTML = ""

			// 未割当てページではないときの処理	
			if(nowUrl != "/taskUnassigned"){
				paginatedData.forEach((item) => {

					const task = document.createElement("div");
				    const priority = document.createElement("span");
				    const taskName = document.createElement("span");
					const dueTime = document.createElement("span");
					const repName = document.createElement("span");
					const status = document.createElement("span");
					const transButton = document.createElement("button");
					let date = new Date(item.dueTime);

					task.className = "task";
					
					task.value = item.id;
				    priority.textContent = item.priority.name;
					priority.className = 'a';
				    taskName.textContent = item.name;
					taskName.className = 'b';
					dueTime.textContent = `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日 ${date.getHours()}時${date.getMinutes()}分`;
					dueTime.className = 'c';
					
					if(item.user != null){
						 repName.textContent = item.user.name;
					 }else{
						 repName.textContent = " ";
					 }
					 repName.className = 'd';
					 status.textContent = item.status.name;
					 status.className = 'e';
					
					 task.appendChild(priority);
					 task.appendChild(taskName);
					 task.appendChild(dueTime);
					 task.appendChild(repName);
					 task.appendChild(status);
					 		 
					 if(item.user != null && item.user.id == loginUser && item.status.id == 1){
							transButton.textContent = "実行";
							transButton.value = item.id;
							transButton.className = 'transButton';
							task.appendChild(transButton);
							 }else if(item.user != null && item.user.id == loginUser && item.status.id == 2){
							transButton.textContent = "完了";
							transButton.value = item.id;
							transButton.className = 'transButton';
							task.appendChild(transButton);
					}
									
					 ul[0].appendChild(task);
					 
				 });

			}else{
				if(taskList != null){
					// 担当者未割り当てタスクページ
					const updateBtnDiv = document.createElement("div");
					const updateButton = document.createElement("button");
					let taskIdList = new Array();
					let userIdList = new Array();
					updateButton.textContent = "更新";
					updateBtnDiv.className ='updateButtonDiv';
					updateButton.className ='btn';
									
					updateBtnDiv.appendChild(updateButton);

					paginatedData.forEach((item) => {


						const task = document.createElement("div");
					    const priority = document.createElement("span");
					    const taskName = document.createElement("span");
						const dueTime = document.createElement("span");
						const repName = document.createElement("span");
						const status = document.createElement("span");
						let date = new Date(item.dueTime);

						task.className = "task";
					    priority.textContent = item.priority.name;
						priority.className = 'a';
					    taskName.textContent = item.name;
						taskName.className = 'b';
						dueTime.textContent = `${date.getFullYear()}年${date.getMonth() + 1}月${date.getDate()}日 ${date.getHours()}時${date.getMinutes()}分`;
						dueTime.className = 'c';
						 status.textContent = item.status.name;
						 status.className = 'e';
						
						 
						 task.appendChild(priority);
						 task.appendChild(taskName);
						 task.appendChild(dueTime);

						 
						 const selectUsers = document.createElement("select");
						 selectUsers.className = 'repAssigned';
						const space = document.createElement("option");
						space.textContent = " ";
						space.value = 0;
						selectUsers.appendChild(space);
					 	
						userList.forEach((u) => {
							const user = document.createElement("option");
											 							
					 		user.value = u.id;
					 		user.textContent = u.name;
					 		selectUsers.append(user);
					 	})
						
						task.appendChild(selectUsers);
						 
						task.appendChild(status);
						 
						 
						 
						 if(item.user != null && item.user.id == loginUser && item.status.id == 1){
								transButton.textContent = "実行";
								transButton.value = item.id;
								transButton.className = 'transButton';
								task.appendChild(transButton);
								 }else if(item.user != null && item.user.id == loginUser && item.status.id == 2){
								transButton.textContent = "完了";
								transButton.value = item.id;
								transButton.className = 'transButton';
								task.appendChild(transButton);

							 }
						 
						taskIdList.push(item.id);
						ul[0].appendChild(task);

					 });
					 ul[0].appendChild(updateBtnDiv);
					 
					updateButton.addEventListener('click',() => {
						const selectUsersAll = document.getElementsByClassName("repAssigned");
						
						for(let obj of selectUsersAll) {
							userIdList.push(obj.value);
						}
						repAssigningClick(taskIdList,userIdList);
					});

				}
			}
			modalOpen(t);
			transBtnClick();
		 };

		 displayData(1);
		
		 
		/**
		 * ページネーション表示関数
		 */
		const displayPagination = () => {

			const totalPages = Math.ceil(data.length / itemsPerPage);
		  	const paginateButton = document.getElementById("paginateButton");
			paginateButton.innerHTML=""
			for (let i = 1; i <= totalPages; i++) {
				const pageButton = document.createElement("button");
		    	pageButton.textContent = i;
		    	
				pageButton.addEventListener("click", (e) => {
		      		const allPageButtons = document.querySelectorAll("#paginateButton button");
		      
					allPageButtons.forEach((button) => button.classList.remove("active"));
					e.target.classList.add("active");
					disabledButton();
					displayData(i);
				});
				
		    	paginateButton.appendChild(pageButton);
			}
		};
		displayPagination();

		/**
		 * ページ読み込み時に最初のボタンをアクティブ化
		 */
		const firstPageButton = document.querySelector("#paginateButton button:first-child");
	  	firstPageButton.classList.add("active");
	  	prev.disabled = true;

		/**
		 * 「<」をクリックした際の処理
		 */
		prev.addEventListener("click", () => {
			const activeNum = parseInt(active[0].textContent);
		  	const prevPage = activeNum > 1 ? activeNum - 1 : 1;
		  	const prevButton = document.querySelector(`#paginateButton button:nth-child(${prevPage})`);
		  	const allPageButtons = document.querySelectorAll("#paginateButton button");
		  	allPageButtons.forEach((button) => button.classList.remove("active"));
		  	prevButton.classList.add("active");
		  	displayData(prevPage);
		  	disabledButton();
		});

		/**
		 * 「>」をクリックした際の処理
		 */
		next.addEventListener("click", () => {
			const activeNum = parseInt(active[0].textContent);
		  	const totalPages = Math.ceil(data.length / itemsPerPage);
		  	const nextPage = activeNum < totalPages ? activeNum + 1 : totalPages;
		  	const nextButton = document.querySelector(`#paginateButton button:nth-child(${nextPage})`);
		  	const allPageButtons = document.querySelectorAll("#paginateButton button");
		  	allPageButtons.forEach((button) => button.classList.remove("active"));
		  	nextButton.classList.add("active");
		  	displayData(nextPage);
		  	disabledButton();
		});


	}


// タスク実行/完了ボタンが押されたときの処理
function transBtnClick(){
	const btns = document.querySelectorAll('.transButton');

	for(var i = 0; i < btns.length; i++){
	    btns[i].addEventListener('click',function(){
			let statusVal = 0;
			
			if(this.textContent == "実行"){
				statusVal = 2;
			}else if(this.textContent == "完了"){
				statusVal = 3;
			}
			this.style.color = 'blue';
			
			// ここで更新したタスクをDBも更新
			transBtnClickDBupdate(this.value,statusVal);

	    },false);
	}
}


// 担当者が更新ボタンを押されたタイミングでDBを更新するPostをなげる
async function repAssigningClick(taskId,userId){
 
	const url = "http://localhost:8080/task/repAssignedUpdate";
	const form = new FormData();
	form.append('id', taskId);
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
		alert("更新しました。");	
	}else{
		alert("更新処理中に問題が発生しました");
	}
	window.location.assign(res.url);

}



// 更新ボタンを押されたタイミングでDBを更新するPostをなげる
async function transBtnClickDBupdate(taskId,statusId){
 
	const url = "http://localhost:8080/task/update";
	const form = new FormData();
	form.append('id', taskId);
	form.append('statusId', statusId);
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
var csrf_token = csrfElm.value;
return csrf_token;
}



		