<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/slotSwap/static/css/common.css">

</head>
<body>
<div class="up-panel">
    <div class="div-up-panel">
        <a href="/slotSwap/home" class="home-button"></a>
    </div>
    <div class="div-up-panel">
        <span class="up-panel-name">SLOT SWAP</span>
    </div>
    <div class="div-up-panel">
    </div>
</div>
<div style="color:red">${errormessage!}</div>

<form action="/slotSwap/usercheck" method="post">
    <div>
        <label>Логин</label>
        <label>
            <input type="text" name="login" class = "input-field">
        </label>
    </div>
    <div>
        <label>Пароль</label>
        <label>
            <input type="password" name="password">
        </label>
    </div>
    <input type="submit" value="Вход">
</form>

<form action="/slotSwap/reg" method="get">
    <input type="submit" value="Регистрация">
</form>

</body>
</html>