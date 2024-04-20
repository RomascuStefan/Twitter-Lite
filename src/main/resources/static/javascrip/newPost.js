async function sendPost() {
    const credential = sessionStorage.getItem("credential");
    if (!credential) {
        alert("Nu sunteți autentificat.");
        window.location.href="../logIn.html";
        return;
    }

    const textInput = document.getElementById('textInput');
    const content = textInput.value.trim();
    if (content === "") {
        alert("Mesajul nu poate fi gol.");
        return;
    }

    const postData = {
        credential: credential,
        content: content
    };

    try {
        const response = await fetch('/add-post', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(postData)
        });

        if (response.ok) {
            alert("Mesajul a fost postat cu succes.");
            textInput.value = "";
            window.location.href="../main-page.html"
        } else {
            const errorMessage = await response.text();
            alert("Eroare la postare: " + errorMessage);
        }
    } catch (error) {
        console.error('Eroare la trimiterea mesajului:', error);
        alert('Eroare la trimiterea mesajului: ' + error.message);
    }
}
