<!DOCTYPE html>
<html lang="ru">
<head>
    <title>User list</title>
    <link rel="stylesheet" href="${context}/static/css/up-panel.css">
    <link rel="stylesheet" href="${context}/static/css/card.css">
    <link rel="stylesheet" href="${context}/static/css/input.css">
    <link rel="stylesheet" href="${context}/static/css/profile.css">
    <script src="${context}/static/js/card.js"></script>
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


<h1>
    Список пользователей
</h1>
<div>
    <div class="card-container">
        <#list users as user>
            <div class="record-card" onclick="selectCard(this, '${user.login}')">
                <div class="record-info">
                    <p><b>Имя</b> ${user.name}</p>
                    <p><b>Фамилия</b> ${user.surname}</p>
                    <p><b>Роль</b> ${user.role}</p>
                </div>
            </div>
        </#list>
    </div>
    <div style = "display: flex; justify-content: center">
        <form method="get" action="${context}/admin/user">
            <input type="hidden" id="selectedRecord" name="selectedUserLogin">
            <input type="submit" value="Показать пользователя" class = "button-login" onclick="return isSelectOnly()">
        </form>
    </div>
</div>
</body>
</html>