<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Registration</title>
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

<form action="/slotSwap/reg" method="post">
    <div>
        <label>Имя</label>
        <input type="text" name="name">
    </div>
    <div>
        <label>Фамилия</label>
        <input type="text" name="surname">
    </div>
    <div>
        <label>Логин</label>
        <input type="text" name="login">
    </div>
    <div>
        <label>Пароль</label>
        <input type="password" name="password">
    </div>
    <input type="submit" value="Готово">
</form>

</body>
</html>