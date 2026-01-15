<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Edit</title>
    <link rel="stylesheet" href="${context}/static/css/up-panel.css">
    <link rel="stylesheet" href="${context}/static/css/input.css">
    <link rel="stylesheet" href="${context}/static/css/error.css">
    <script src="${context}/static/js/error.js"></script>
</head>
<body>
<div class="up-panel">
    <div class="div-up-panel">
        <a href="${context}/home" class="home-button"></a>
    </div>
    <div class="div-up-panel">
        <span class="up-panel-name">SLOT SWAP</span>
    </div>
    <div class="div-up-panel">
        <a href="${context}/user" class="profile-button"></a>
    </div>
</div>

<#if errormessage??>
    <div id = "error-block" class="error" onclick="hide()">
        <span class="text">${errormessage}</span>
    </div>
</#if>

<form action="${context}/user/update" method="post">
    <div class="form-wrapper">
        <div>
            <label>
                <input type="text" name="name" value="${user.name}" placeholder="Имя" required maxlength="50">
            </label>
        </div>
        <div>
            <label>
                <input type="text" name="surname" value="${user.surname}" placeholder="Фамилия" required maxlength="50">
            </label>
        </div>
        <div>
            <label>
                <input type="password" name="oldPassword" placeholder="Старый Пароль" required minlength="8" maxlength="100">
            </label>
        </div>
        <div>
            <label>
                <input type="password" name="newPassword" placeholder="Новый Пароль" required minlength="8" maxlength="100">
            </label>
        </div>
        <div class="buttons" style="justify-content: center">
            <div class="div-button">
                <input type="submit" value="Готово" class="button-login" onclick="return confirm('Подтвердите обновление данных')">
            </div>
        </div>
    </div>
</form>
</body>
</html>