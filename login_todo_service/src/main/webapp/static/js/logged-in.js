  const setting_btn = document.querySelector(".settings_btn");
  const setting_tab = document.querySelector("header .changeInfo");
  [on, off] = [true, false];
  setting_btn.addEventListener("click", () => {
    setting_tab.style.height = on === true ? 700 + "px" : 0 + "px";
    setting_tab.style.border = on === true ? "3px solid black" : "none";
    [on, off] = [off, on];
  });

  const manage_todos = document.querySelector(".todos__manage");
  const quick_add = document.querySelector(".quick_add");
  const todos_btn = document.querySelector(".todos__btn");
  todos_btn.onclick = () =>
    (manage_todos.style.width =
      manage_todos.style.width !== "400px" ? "400px" : "0px");
  quick_add.onclick = () => 
    (manage_todos.style.width =
      manage_todos.style.width !== "400px" ? "400px" : "0px");
      
  const view_btn = document.querySelector(".view_btn");
  const tab = document.querySelector(".tab");
  view_btn.onclick = () =>
    (tab.style.width = tab.style.width !== "0px" ? "0px" : "305px");
  
  const states = document.querySelectorAll(".newState");
  const importance = document.querySelectorAll(".newImportance");
  for(one of states) {
  		one.style.background = one.value !== "doing" ? "linear-gradient(to bottom, hotpink 0%, hotpink 100%)" : "linear-gradient(to bottom, pink 0%, pink 100%)";
  		one.style.border = "2px solid #412475";
		one.style.color = "white";
	}
  for(one of importance) {
		one.style.background = one.value !== "0" ? "linear-gradient(to bottom, #667eea 0%, #667eea 100%)" : "linear-gradient(to bottom, #ffab8c  0%, #ffab8c  100%)"
		one.style.border = "2px solid #412475";
		one.style.color = "white";
	}
  
