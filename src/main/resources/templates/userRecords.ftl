<!DOCTYPE html>
<html lang="ru">
<h1>
    Запланированные смены
</h1>
<div>
    <table>
        <thead>
        <tr>
            <th>Тип</th>
            <th>Дата</th>
            <th>Время</th>
            <th>Название</th>
            <th>Статус</th>
        </tr>
        </thead>
        <tbody>
        <#list sheduledAndExchangedRecords as record>
            <tr>
                <td data-label="Тип">${record.type}</td>
                <td data-label="Дата">${record.date}</td>
                <td data-label="Время">${record.time}</td>
                <td data-label="Название">${record.name!"Отсутствует"}</td>
                <td data-label="Статус">${record.status}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
<div style="color:red">${errormessage!}</div>
<form method="post" action="/slotSwap/user/records">
    <div>
        <label>Выбрать смену для обмена</label>
        <select name="choosedRecordId">
            <#list sheduledRecords as record>
                <option value = "${record.id}">${record.type}, ${record.date}, ${record.time}, ${record.status}</option>
            </#list>
        </select>
    </div>
    <div>
        <input type="submit" value="Выставить на обмен">
    </div>
</form>
<h1>
    Завершенные смены
</h1>
<div>
    <table>
        <thead>
        <tr>
            <th>Тип</th>
            <th>Дата</th>
            <th>Время</th>
            <th>Название</th>
            <th>Количество чатов</th>
            <th>Комментарий</th>
            <th>Статус</th>
        </tr>
        </thead>
        <tbody>
        <#list completedRecords as record>
            <tr>
                <td data-label="Тип">${record.type}</td>
                <td data-label="Дата">${record.date}</td>
                <td data-label="Время">${record.time}</td>
                <td data-label="Название">${record.name!"Отсутствует"}</td>
                <td data-label="Количество чатов">${record.chatsCount!"Не заполнено"}</td>
                <td data-label="Комментарий">${record.comment!"Отсутствует"}</td>
                <td data-label="Статус">${record.status}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>
<form action="/slotSwap/home" method="get">
    <input type="submit" value="Домой">
</form>

</html>
