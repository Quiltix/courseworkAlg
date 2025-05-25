document.addEventListener('DOMContentLoaded', () => {
    if (!localStorage.getItem('token')) {
        window.location.href = '/login';
        return;
    }

    const logoutBtn = document.getElementById('logout-btn');
    const searchForm = document.querySelector('.search-form');
    const resultDiv = document.getElementById('result');
    const userHistoryDiv = document.getElementById('user-history');
    const allHistoryDiv = document.getElementById('all-history');

    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('token');
        window.location.href = '/login';
    });

    searchForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const text = document.getElementById('string').value.trim();
        const substring = document.getElementById('substring').value.trim();

        if (!text || !substring) {
            showError("Заполните оба поля!");
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/algorithm', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                body: JSON.stringify({ text, substring })
            });

            if (response.status === 401) {
                localStorage.removeItem('token');
                window.location.href = '/login';
                return;
            }

            const data = await response.json();
            showResult(data);
            loadHistory();
        } catch (error) {
            showError(error.message);
        }
    });

    function showError(text) {
        resultDiv.innerHTML = `<div class="error">${text}</div>`;
    }

    function showResult(data) {
        const resultHTML = data.length > 0
            ? `Найденные позиции: ${data.join(', ')}`
            : `<div class="error">Подстрока не найдена</div>`;
        resultDiv.innerHTML = resultHTML;
    }

    function createHistoryCard(algorithm) {
        return `
            <div class="history-card">
                <h4>Запрос #${algorithm.id}</h4>
                <p><strong>Текст:</strong> ${algorithm.text}</p>
                <p><strong>Подстрока:</strong> ${algorithm.substring}</p>
                <p><strong>Результат:</strong> ${algorithm.result.join(', ') || 'не найдено'}</p>
            </div>
        `;
    }

    async function loadHistory() {
        try {
            const token = localStorage.getItem('token');
            const [userResponse, allResponse] = await Promise.all([
                fetch('http://localhost:8080/api/algorithm', {
                    headers: { 'Authorization': `Bearer ${token}` }
                }),
                fetch('http://localhost:8080/api/algorithm/all', {
                    headers: { 'Authorization': `Bearer ${token}` }
                })
            ]);

            if (!userResponse.ok || !allResponse.ok) throw new Error('Ошибка загрузки истории');

            const userHistory = await userResponse.json();
            const allHistory = await allResponse.json();

            userHistoryDiv.innerHTML = userHistory.map(createHistoryCard).join('');
            allHistoryDiv.innerHTML = allHistory.map(createHistoryCard).join('');
        } catch (error) {
            showError(error.message);
        }
    }

    loadHistory();
});