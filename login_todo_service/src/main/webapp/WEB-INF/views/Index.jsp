<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Welcome</title>
    <link rel="stylesheet" href="/login_todo_service/static/css/index.css?ver=1">
  </head>
  <body>
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
            autocomplete="off"
          />
          <input
            type="password"
            required
            placeholder="password"
            class="password"
            name="password"
            autocomplete="off"
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
        <span class="sspan">or use your email for registeration</span>
        <form class="form-container" action="sign-up" method="post">
          <input
            type="text"
            required
            placeholder="name"
            name="name"
            class="name"
            autocomplete="off"
          />
          <input
            type="text"
            required
            placeholder="email"
            name="email"
            class="email"
            autocomplete="off"
          />
          <input
            type="text"
            required
            placeholder="username"
            name="username"
            class="username"
            autocomplete="off"
          />
          <input
            type="password"
            required
            placeholder="password"
            name="password"
            class="password"
            autocomplete="off"
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
    <script src="/login_todo_service/static/js/index.js?ver=1">
    </script>
    <script
      src="https://kit.fontawesome.com/3e27d22283.js"
      crossorigin="anonymous"
    ></script>
  </body>
</html>
