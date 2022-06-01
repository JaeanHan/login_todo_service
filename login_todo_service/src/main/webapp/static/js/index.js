const pannel = document.getElementById("pannel");
  const header = document.querySelector("#pannel h2");
  const span = document.querySelector("#pannel p");
  const btn = document.querySelector("#pannel button");
  const signin = document.querySelector(".signin-container");
  const signup = document.querySelector(".signup-container");
  
  function onClick() {
    const value = pannel.value;
    console.log(value);
    if (value !== "1") {
      header.innerHTML = "Welcome Back!";
      span.innerHTML =
        "To keep connected with us please login<br/>with your personal info<br/>";
      btn.innerHTML = "SIGN IN";
      pannel.classList.remove("moveControl");
      signup.classList.remove("toLeft");
      signin.classList.add("toRight");
      pannel.style.background =
        "linear-gradient(to right, deeppink 50%, hotpink 50%)";
      pannel.value = "1";
    } else {
      header.innerHTML = "Hello, Friend!";
      span.innerHTML =
        "Enter your personal details and start<br/>journey with us!<br/>";
      btn.innerHTML = "SIGN UP";
      pannel.classList.add("moveControl");
      signin.classList.remove("toRight");
      signup.classList.add("toLeft");
      pannel.style.background =
        "linear-gradient(to left, deeppink 50%, hotpink 50%)";
      pannel.value = "0";
    }
  }
  
  btn.addEventListener("click", onClick);