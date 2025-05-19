document.addEventListener('DOMContentLoaded', () => {
    const submitBtn = document.getElementById('submit-btn');
    const stringInput = document.getElementById('string');
    const substringInput = document.getElementById('substring');
    const resultH3 = document.getElementById('result');

    const authBtn = document.getElementById('auth-btn'); 
    const registerBtn = document.getElementById('register-btn'); 

    const userHistorySection = document.querySelector('.user-history');
    const allHistorySection = document.querySelector('.all-history');

    function showError(text) {
        resultH3.textContent = text;
        resultH3.style.color = '#ff3d3d';
    }

    function showResult(text) {
        if (!text || text.length === 0) {
            resultH3.textContent = "Подстрока не найдена";
            resultH3.style.color = '#ff3d3d';
        } else {
            resultH3.textContent = `Результат: ${text.join(', ')}`;
        }
    }

    function createCard(algorithm) {
        const card = document.createElement('div');
        card.classList.add('card');
        card.innerHTML = `
            <h4>ID: ${algorithm.id}</h4>
            <p>Текст: ${algorithm.text}</p>
            <p>Подстрока: ${algorithm.substring}</p>
            <p>Результаты: ${algorithm.result.join(', ')}</p>
        `;
        return card;
    }

    async function register(username, password) {
        try {
            const response = await fetch('http://localhost:8080/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });
            
            if (!response.ok) throw new Error('Ошибка при регистрации');
            return await response.json();
        } catch (error) {
            console.error('Ошибка:', error);
            throw error;
        }
    }

    async function login(username, password) {
        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });
            
            if (!response.ok) throw new Error('Ошибка при авторизации');
            const data = await response.json();
            localStorage.setItem('token', data.accessToken);
            location.reload()
            return data;

        } catch (error) {
            console.error('Ошибка:', error);
            throw error;
        }
    }

    

    authBtn?.addEventListener('click', async () => {
        const username = prompt('Введите логин:');
        const password = prompt('Введите пароль:');
        
        if (username && password) {
            try {
                await login(username, password);
                alert('Авторизация успешна!');
            } catch (error) {
                alert('Ошибка при авторизации: ', error.message);
            }
        }
    });

    registerBtn?.addEventListener('click', async () => {
        const username = prompt('Введите новый логин:');
        const password = prompt('Введите пароль:');
        
        if (username && password) {
            try {
                await register(username, password);
                alert('Регистрация успешна!');
            } catch (error) {
                alert('Ошибка при регистрации: ', error.message);
            }
        }
    });

    submitBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        
        const text = stringInput.value.trim();
        const substring = substringInput.value.trim();

        if (!text || !substring) {
            showError("Заполните оба поля!");
            return;
        }

        try {
            const token = localStorage.getItem('token');
            if (!token) {
                showError('Войдите в аккаунт!');
                return;
            }

            const response = await fetch('http://localhost:8080/api/algorithm', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    text: text,  
                    substring: substring
                })
            });

            if (response.status === 401) {
                showError('Перезайдите в аккаунт!');
                localStorage.removeItem('token');
                return;
            }

            if (!response.ok) {
                throw new Error(`Ошибка: ${response.status}`);
            }

            const data = await response.json();
            showResult(data);

            
        } catch (error) {
            console.error('Ошибка:', error);
            showError(error.message);
        }
    });

    async function fetchAlgorithms() {
        const token = localStorage.getItem('token');
        try {
            if (token) {
                const response = await fetch('http://localhost:8080/api/algorithm/all', {
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                
                if (!response.ok) { 
                    throw new Error('Ошибка при загрузке данных')
                }

                const data = await response.json();
                data.forEach(algorithm => {
                const card = createCard(algorithm);
                allHistorySection.appendChild(card)});
            }
            
            
        } catch (error) {
            console.error('Ошибка получения данных:', error);
            userHistorySection.innerHTML = '<p class="error">Ошибка при загрузке данных</p>';
        }
    }
    
    async function fetchUserAlgorithms() {
        const token = localStorage.getItem('token');
        try {
            if (token) {
                const response = await fetch('http://localhost:8080/api/algorithm', {
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                
                if (!response.ok) { 
                    throw new Error('Ошибка при загрузке данных')
                }
                const data = await response.json();
                data.forEach(algorithm => {
                const card = createCard(algorithm);
                userHistorySection.appendChild(card)});
            }
            
            
        } catch (error) {
            console.error('Ошибка получения данных:', error);
            allHistorySection.innerHTML = '<p class="error">Ошибка при загрузке данных</p>';
        }
    }



    stringInput.addEventListener('input', () => resultH3.textContent = '');
    substringInput.addEventListener('input', () => resultH3.textContent = '');

    fetchAlgorithms();
    fetchUserAlgorithms();
});