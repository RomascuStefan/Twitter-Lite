function handleCreateAccountForm(event) {
    event.preventDefault();

    const formData = {
        id: document.getElementById("username").value,
        first_name: document.getElementById("firstName").value,
        last_name: document.getElementById("lastName").value,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    };

    fetch('/createAccountForm', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok) {
                console.log('Cont creat cu succes');
                window.location.href='../index.html'
                alert("cont creat cu succes");
            } else {
                console.error('Crearea contului a eșuat');
                response.text().then(errorMessage => {
                    alert("Creare contului a esuat. "+errorMessage);
                });
            }
        })
        .catch(error => {
            console.error('Eroare la crearea contului:', error);
            alert('A apărut o eroare la crearea contului. Vă rugăm să încercați din nou mai târziu.');
        });
}