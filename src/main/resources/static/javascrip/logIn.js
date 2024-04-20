function handleLoginForm(event) {
    event.preventDefault();

    const authType = document.getElementById("authType").value;
    const credential = document.getElementById("credential").value;
    const password = document.getElementById("password").value;

    const formData = {
        authType: authType,
        credential: credential,
        password: password
    };

    fetch('/logInForm', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                sessionStorage.setItem("authType",formData.authType);
                sessionStorage.setItem("credential",formData.credential);

                window.location.href = '../main-page.html'; // Redirectăm către pagina principală în caz de succes
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
