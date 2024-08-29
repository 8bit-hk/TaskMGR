'use strict'
window.onload =  function(){
	userListView(userList,taskList);
}

function jobCheck(){
	
	const checkboxes = document.getElementsByClassName("is-job");
	
	let ul = new Array();
	let tl = new Array();

	tl = taskList;
	
	// roleId リーダ=2 / メンバ=1
	if(checkboxes[0].checked && checkboxes[1].checked){
		// リーダ、メンバ両方チェック
		ul = userList;

	}else if(checkboxes[0].checked && !checkboxes[1].checked){
		// リーダのみチェック
		userList.forEach(function(user){
			if(user.role.id == 2){
				ul.push(user);
			}
		})
	
	}else if(!checkboxes[0].checked && checkboxes[1].checked){
		// メンバのみチェック
		userList.forEach(function(user){
			if(user.role.id == 1){
				ul.push(user);
			}
		})
	}else{
		// リーダ、メンバ両方チェックなし
		ul = new Array();

	}
	userListView(ul,tl);
}

function userListView(uL,tL){
	const ul = document.getElementsByClassName("userList");
	const prev = document.getElementById("prev");
	const next = document.getElementById("next");
	const active = document.getElementsByClassName("active");

	// ページネーション用のデータ配列
	const　data = uL;
	const taskList = tL;
	
	// 1ページあたりの表示数
	const itemsPerPage = 5;

	// ページネーションボタンをクリック時のdisabledの指定
	const disabledButton = () => {
		// 最初のページネーションのボタンがactiveの場合は「<」をdisabledに
		const firstPageButton = document.querySelector("#paginateButton button:first-child");
  		firstPageButton.classList.contains("active") ? (prev.disabled = true) : (prev.disabled = false);
		
		// 最後のページネーションのボタンがactiveの場合は「>」をdisabledに
  		const lastPageButton = document.querySelector("#paginateButton button:last-child");
  		lastPageButton.classList.contains("active") ? (next.disabled = true) : (next.disabled = false);
	};

	// データ表示の関数
	const displayData = (page) => {
		
		const startData = (page - 1) * itemsPerPage;
		const endData = startData + itemsPerPage;
		const paginatedData = data.slice(startData, endData);

	  	ul[0].innerHTML = ""
		
	  	paginatedData.forEach((item) => {
		
			const member = document.createElement("div");
		    const job = document.createElement("span");
		    const name = document.createElement("span");
			const notStartedVal = document.createElement("span");
			const inProgressVal = document.createElement("span");
			const completedVal = document.createElement("span");
			
			member.className = 'user';
			if(item.role.id == 1){
				job.textContent = "メンバ";
			}else{
				job.textContent = "リーダ";					
			}
			job.className = 'job';
		    name.textContent = item.name;
			name.className = 'name';
			let nVal = 0;
			let iVal = 0;
			let cVal = 0;
			taskList.forEach(function(task){
				
				if(task.user != null){
					if(task.user.id == item.id){
						if(task.status.id == 1){
							// 未着手
							 nVal += 1;
						}else if(task.status.id == 2){
							// 進行中
							iVal += 1;
						}else if(task.status.id == 3){
							// 完了済
							cVal += 1;
						}
					}
				}
			})
			
			notStartedVal.textContent = nVal;
			inProgressVal.textContent = iVal;
			completedVal.textContent = cVal;
			notStartedVal.className = 'notStarted';
			inProgressVal.className = 'inProgress';
			completedVal.className = 'completed';

			 member.appendChild(job);
			 member.appendChild(name);
			 member.appendChild(notStartedVal);
			 member.appendChild(inProgressVal);
			 member.appendChild(completedVal);
			 ul[0].appendChild(member);
		 });
	 };
	 displayData(1);

	// ページネーション表示関数
	const displayPagination = () => {

		const totalPages = Math.ceil(data.length / itemsPerPage);
	  	const paginateButton = document.getElementById("paginateButton");
		paginateButton.innerHTML=""
		if(totalPages == 1){
			next.disabled = true;
		}
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

	// ページ読み込み時に最初のボタンをアクティブ化
	const firstPageButton = document.querySelector("#paginateButton button:first-child");
	if(firstPageButton != null){
		firstPageButton.classList.add("active");
	}else{
		next.disabled = true;
	}
  	prev.disabled = true;

	// 「<」をクリックした際の処理
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

	// 「>」をクリックした際の処理
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

