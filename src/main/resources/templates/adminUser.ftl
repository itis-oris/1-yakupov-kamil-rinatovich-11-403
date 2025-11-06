<!DOCTYPE html>
<html lang="ru">
<div>
    <label>Имя</label>
    <label>${selectedUser.name}</label>
</div>
<div>
    <label>Фамилия</label>
    <label>${selectedUser.surname}</label>
</div>
<form action="/slotSwap/admin/user/update" method="post">

    <div>
        <label>Статус</label>
        <select name="selectedUserRole">
            <#if selectedUser.role == 'Стажер'>
                <option value="Стажер" selected>Стажер</option>
            <#else>
                <option value="Стажер">Стажер</option>
            </#if>
            <#if selectedUser.role == 'Младший куратор'>
                <option value="Младший куратор" selected>Младший куратор</option>
            <#else>
                <option value="Младший куратор">Младший куратор</option>
            </#if>
            <#if selectedUser.role == 'Старший куратор'>
                <option value="Старший куратор" selected>Старший куратор</option>
            <#else>
                <option value="Старший куратор">Старший куратор</option>
            </#if>
            <#if selectedUser.role == 'Админ'>
                <option value="Админ" selected>Админ</option>
            <#else>
                <option value="Админ">Админ</option>
            </#if>
        </select>
    </div>
    <input type="submit" value="Готово">
</form>
<form action="/slotSwap/admin/user/delete" method="post">
    <input type = 'hidden' name = 'selectedUserLogin' value = "${selectedUser.login}">
    <input type="submit" value="Удалить пользователя">
</form>
</html>