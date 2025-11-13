let selectedCard = null;
function selectCard(card, value) {
    if (selectedCard) selectedCard.classList.remove('selected');
    selectedCard = card;
    card.classList.add('selected');
    document.getElementById('selectedRecord').value = value;
}
function isSelect() {
    if (selectedCard) {
        return confirm('Подтвердите ваше действие')
    } else {
        alert('Пожалуйста, выберите смену')
        return false;
    }
}
function isSelectOnly() {
    if (selectedCard) {
        return true;
    } else {
        alert('Пожалуйста, выберите пользователя')
        return false;
    }
}