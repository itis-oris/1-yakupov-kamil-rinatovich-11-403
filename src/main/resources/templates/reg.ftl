<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <meta name="description" content="Регистрация">

</head>
<body>

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