function handleLoginForm(event) {
    event.preventDefault(); // Prevenim comportamentul implicit de trimitere a formularului

    // Colectăm datele formularului
    const authType = document.getElementById("authType").value;
    const credential = document.getElementById("credential").value;
    const password = document.getElementById("password").value;

    // Construim obiectul cu datele de autentificare
    const formData = {
        authType: authType,
        credential: credential,
        password: password
    };

    // Trimitem cererea AJAX
    fetch('/logIn', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '../mainPage.html'; // Redirectăm către pagina principală în caz de succes
            } else {
                console.error('LogIn esuat');
                response.text().then(errorMessage => {
                    alert("Conectarea în cont a eșuat. " + errorMessage);
                });
            }
        })
        .catch(error => {
            console.error('Eroare la autentificare:', error);
            alert('Autentificare eșuată. Vă rugăm să verificați credențialele introduse.');
        });
}
