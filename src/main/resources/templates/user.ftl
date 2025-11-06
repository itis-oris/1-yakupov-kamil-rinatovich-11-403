<!DOCTYPE html>
<html lang="ru">

<h1>
    Ваш профиль, ${user.name} ${user.surname}!
</h1>
<div>
    ${user.login}
</div>
<div>
    ${user.role}
</div>
<form action="/slotSwap/user/update" method="get">
    <input type="submit" value="Изменить данные">
</form>
<form action="/slotSwap/user/records" method="get">
    <input type="submit" value="Посмотреть мои смены">
</form>
<form action="/slotSwap/login" method="get">
    <input type="submit" value="Выйти">
</form>
<form action="/slotSwap/home" method="get">
    <input type="submit" value="Домой">
</form>

</html>