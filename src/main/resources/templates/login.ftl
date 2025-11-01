<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Тест Freemarker</title>
    <meta name="description" content="Тест Freemarker">

</head>
<body>

<div style="color:red">${errormessage!}</div>

<form action="/slotSwap/usercheck" method="post">
    <div>
        <label>Логин</label>
        <input type="text" name="login">
    </div>
    <div>
        <label>Пароль</label>
        <input type="password" name="password">
    </div>
    <input type="submit" value="Вход">
</form>

<form action="/slotSwap/reg" method="get">
    <input type="submit" value="Регистрация">
</form>

</body>
</html>