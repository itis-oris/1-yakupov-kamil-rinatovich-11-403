<!DOCTYPE html>
<html lang="ru">
<head>
    <title>User Editor</title>
    <link rel="stylesheet" href="${context}/static/css/up-panel.css">
    <link rel="stylesheet" href="${context}/static/css/input.css">
    <link rel="stylesheet" href="${context}/static/css/card.css">
    <link rel="stylesheet" href="${context}/static/css/profile.css">

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
<div class="container">
    <div class="block">
        <div class="buttons-div"
             style="border-bottom-left-radius: 0; border-bottom-right-radius: 0; pointer-events: none;">
            <a> <b>Имя: </b><span style="margin-left: 8px"> ${selectedUser.name}</a>
        </div>
        <div class="buttons-div" style="border-top-left-radius: 0; border-top-right-radius: 0; pointer-events: none;">
            <a> <b>Фамилия: </b><span style="margin-left: 8px"> ${selectedUser.surname}</a>
        </div>
    </div>

    <form action="${context}/admin/user/update" method="post" class="select-record-type">
        <div class="form-wrapper" style>
            <label style="margin-bottom: 3px">Выберите роль</label>
            <label>
                <select name="selectedUserRole">
                    <option value="Стажер" <#if selectedUser.role == "Стажер">selected</#if>>Стажер</option>
                    <option value="Младший куратор" <#if selectedUser.role == "Младший куратор">selected</#if>>
                        Младший куратор
                    </option>
                    <option value="Старший куратор" <#if selectedUser.role == "Старший куратор">selected</#if>>
                        Старший куратор
                    </option>
                    <option value="Админ" <#if selectedUser.role == "Админ">selected</#if>>Админ</option>
                </select>
            </label>
            <input type="submit" value="Готово" class="button-login" onclick="return confirm('Подтвердите изменение роли')">
        </div>
    </form>

    <div class="block">
        <div class="buttons-div" style="background-color: black">
            <form action="${context}/admin/user/delete" method="post">
                <input type="submit" value="Удалить пользователя" class="button-login" onclick="return confirm('Подтвердите удаление аккаунта')">
            </form>
        </div>

    </div>

</div>
</body>
</html>