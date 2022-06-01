<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <!-- <link rel="stylesheet" href="/login_todo_service/static/css/index.css"> 억까 그만! -->
    <style type="text/css">
    
    @import url('https://fonts.googleapis.com/css2?family=Nunito:wght@400;700&display=swap');

	body {
	    width: 100vw;
	    height: 100vh;
	    font-family: 'Nunito', sans-serif;
	    background-color: #ccc;
	    font-size: 1vw;
	}
	
	.container {
	    width: 60%;
	    height: 60%;
	    display: flex;
	    justify-content: space-between;
	    margin: 0 auto;
	    margin-top: 10%;
	    box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
	    border-radius: 10px;
	    position: relative;
	    overflow: hidden;
	    background-color: white;
	}
	
	.signin-container,
	.signup-container {
	    display: flex;
	    width:100%;
	    height: 100%;
	    flex-direction: column;
	    align-items: center;
	    background-color: white;
	    transition: 0.5s;
	    opacity: 1;
	}
	
	.signin-container h2,
	.signup-container h2 {
	    font-size: 2.3rem;
	    font-weight: 700;
	}
	
	.icons {
	    display: flex;
	    justify-content: center;
	    justify-content: space-between;
	    width: 26%;
	    margin-bottom: 4%;
	}
	
	.icons a {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    height: 30px;
	    width: 30px;
	    border-radius: 50%;
	    border: 1px solid black;
	    color: black;
	    text-decoration: none;
	    opacity: 0.65;
	}
	
	.container .sspan {
	    margin-bottom: 1%;
	}
	
	.signin-container .form-container {
	    display: flex;
	    flex-direction: column;
	    width: 80%;
	    height: 55%;
	    justify-content: space-between;
	    align-items: center;
	    margin-bottom: 5%;
	    box-sizing: border-box;
	}
	
	.signin-container .form-container .username,
	.signin-container .form-container .password {
	    padding-left: 3%;
	    width: 100%;
	    height: 20%;
	    font-size: 1.3rem;
	    border:none;
	    background-color: #ccc;
	    transition: 0.5s;
	    justify-content: center;
	    border-radius: 15px;
	}
	
	.signin-container .form-container .username {
	    margin-bottom: 3%;
	}
	
	.help {
	    text-decoration: none;
	    color:black;
	    margin-top: 10%;
	
	}
	
	.submit {
	    border:none;
	    width: 180px;
	    height: 50px;
	    border-radius: 25px;
	    background-color: #FF69B4;
	    color: white;
	    font-size: 1.2rem;
	    transition: 0.5s;
	}
	
	.signin-container .submit {
	    margin-bottom: 5%;
	}
	
	.submit:hover {
	    background-color: #FF1493 ;
	
	}
	
	.submit:last-child {
	    margin-top: 20px;
	}
	
	.signup-container .form-container {
	    width: 80%;
	    height: 50%;
	    display: flex;
	    flex-direction: column;
	    justify-content: space-between;
	    align-items: center;
	
	}
	
	/* .signup-container .form-container .name, */
	.signup-container .form-container .email,
	.signup-container .form-container .username,
	.signup-container .form-container .password {
	    padding-left: 3%;
	    width: 100%;
	    height: 20%;
	    font-size:1.2rem;
	    border: none;
	    background-color: #ccc;
	    transition: 0.5s;
	    justify-content: center;
	    border-radius: 15px;
	}
	
	/* .signup-container .form-container .name:focus, */
	.signin-container .form-container .username:focus,
	.signin-container .form-container .password:focus,
	.signup-container .form-container .email:focus,
	.signup-container .form-container .username:focus,
	.signup-container .form-container .password:focus {
	    outline:none;
	    /* border-radius: 0px; */
	    border: 2px solid hotpink;
	
	}
	
	#pannel {
	    position:absolute;
	    width: 50%;
	    height: 100%;
	    z-index: 10;
	    background: linear-gradient(to left, #FF1493 50%, #FF69B4 50%);
	    top:0;
	    transition: 0.5s;
	    display: flex;
	    flex-direction: column;
	    justify-content: center;
	    align-items: center;
	    color:white;
	}
	
	#pannel h2 {
	    font-size: 3.2rem;
	    margin-bottom: 0;
	}
	
	#pannel p {
	    text-align: center;
	    font-size: 1.2rem;
	}
	
	#pannel button {
	    width: 150px;
	    height: 50px;
	    text-align: center;
	    background-color: transparent;
	    border: 1px solid white;
	    color:white;
	    border-radius: 20px;
	    font-size: 1.2rem;
	    /* margin-top: 115px; */
	}
	
	.moveControl {
	    transform: translateX(100%);
	}
	
	.toRight {
	    opacity: 0;
	    transform: translateX(20%);
	}
	
	.toLeft {
	    opacity: 0;
	    transform: translateX(-20%);
	}
	
	    
    </style>
  </head>
  <body>
    <div class="container">
      <div class="signin-container">
        <h2>Sign in</h2>
        <div class="icons">
          <a href=""><i class="fa-brands fa-facebook-f"></i></a>
          <a href=""><i class="fa-brands fa-google-plus-g"></i></a>
          <a href=""><i class="fa-brands fa-linkedin-in"></i></a>
        </div>
        <span class="sspan">or use your account</span>
        <form class="form-container" action="sign-in" method="post">
          <input
            type="text"
            required
            placeholder="username"
            class="username"
            name="username"
          />
          <input
            type="password"
            required
            placeholder="password"
            class="password"
            name="password"
          />
          <a href="" class="help">Forgot password?</a>
          <input type="submit" value="SIGN IN" class="submit" />
        </form>
        <!-- <form> -->
        <!-- </form> -->
      </div>
      <div class="signup-container">
        <h2>Create Account</h2>
        <div class="icons">
          <a href=""><i class="fa-brands fa-facebook-f"></i></a>
          <a href=""><i class="fa-brands fa-google-plus-g"></i></a>
          <a href=""><i class="fa-brands fa-linkedin-in"></i></a>
        </div>
        <span class="sspan">or use your email for registration</span>
        <form class="form-container" action="sign-up" method="post">
          <!-- <input
            type="text"
            required
            placeholder="name"
            name="name"
            class="name"
          /> -->
          <input
            type="text"
            required
            placeholder="email"
            name="email"
            class="email"
          />
          <input
            type="text"
            required
            placeholder="username"
            name="username"
            class="username"
          />
          <input
            type="password"
            required
            placeholder="password"
            name="password"
            class="password"
          />
          <input type="submit" value="SIGN UP" class="submit" />
        </form>
        <!-- <form> -->
        <!-- </form> -->
      </div>
      <div id="pannel" class="moveControl">
        <h2>Hello, Friend!</h2>
        <p>Enter your personal details and start<br />journey with us!<br /></p>
        <button>SIGN UP</button>
      </div>
    </div>
    <script src="/login_todo_service/static/js/index.js">
    </script>
    <script
      src="https://kit.fontawesome.com/3e27d22283.js"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
