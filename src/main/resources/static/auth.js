document.addEventListener('DOMContentLoaded', () => {
    const tabs = document.querySelectorAll('.tab');
    const forms = document.querySelectorAll('.auth-form');
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');

    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            const tabName = tab.dataset.tab;

            tabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');

            forms.forEach(form => {
                form.classList.remove('active');
                if(form.id === `${tabName}Form`) {
                    form.classList.add('active');
                }
            });
        });
    });

    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const errorElement = document.getElementById('loginError');
        errorElement.textContent = '';

        const username = document.getElementById('loginUsername').value.trim();
        const password = document.getElementById('loginPassword').value.trim();

        if(!username || !password) {
            errorElement.textContent = 'Заполните все поля';
            return;
        }

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if(!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Ошибка авторизации');
            }

            const { accessToken } = await response.json();
            localStorage.setItem('token', accessToken);
            window.location.href = '/';
        } catch (error) {
            errorElement.textContent = error.message;
        }
    });


    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const errorElement = document.getElementById('registerError');
        errorElement.textContent = '';

        const username = document.getElementById('registerUsername').value.trim();
        const password = document.getElementById('registerPassword').value.trim();

        if(password.length < 6) {
            errorElement.textContent = 'Пароль должен быть не менее 6 символов';
            return;
        }

        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if(!response.ok) {
                const error = await response.json();
                throw new Error(error.message || 'Ошибка регистрации');
            }

            errorElement.textContent = 'Регистрация успешна! Теперь войдите';
            setTimeout(() => {
                document.querySelector('.tab[data-tab="login"]').click();
            }, 2000);
        } catch (error) {
            errorElement.textContent = error.message;
        }
    });

    if(localStorage.getItem('token')) {
        window.location.href = '/';
    }
});