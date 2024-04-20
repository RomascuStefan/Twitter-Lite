function logOut()
{
    sessionStorage.clear();
    window.location.href='../index.html';
    alert("Successfully logged out!");
}


function searchProfile() {
    const userIdTextbox = document.getElementById('userIdTextbox');
    const userId = userIdTextbox.value.trim();

    if (!userId) {
        alert('Please enter a user ID.');
        return;
    }
    if (userId.includes("@"))
        window.location.href = `profile.html?email=${userId}`;
    else
        window.location.href = `profile.html?userId=${userId}`;


}

async function loadSerchProfile() {


    if (!sessionStorage.getItem("credential")) {
        alert("Not logged in");
        window.location.href = "../logIn.html";
        return;
    }


    const userIdHeading = document.getElementById('userIdHeading');
    const postsContainer = document.getElementById('postsContainer');

    const urlParams = new URLSearchParams(window.location.search);
    const userId = urlParams.get('userId');
    const email = urlParams.get('email');


    try {
        var userExistsResponse;
        if (userId) {
            userExistsResponse = await fetch(`/user/${userId}`);

        } else if (email) {
            userExistsResponse = await fetch(`/email/${email}`);

        } else {
            alert('Invalid access. No user identifier provided.');
            return;
        }
        if (!userExistsResponse.ok) {
            alert('User does not exist.');
            window.location.href = "../main-page.html";
            return;
        }

        if(userId)
            await loadViewUserNumbers(userId);
        else
            await loadViewUserNumbers(email);

        var response;
        if (userId)
            response = await fetch(`/view-profile-user/${userId}`);
        else if (email)
            response = await fetch(`/view-profile-email/${email}`);
        else {
            alert('Invalid access. No user identifier provided.');
            return;
        }
        if (!response.ok) {
            alert(`Failed to fetch posts for user`);
            return;
        }


        const posts = await response.json();

        userIdHeading.textContent = userId ? `User ID: ${userId}` : `Email: ${email}`;

        posts.forEach(post => {
            console.log("ceva");
            const postElement = document.createElement('div');
            postElement.classList.add('post');

            // Creaza și adauga elementul pentru IDul utilizatorului
            const userIdElement = document.createElement('h3');
            userIdElement.textContent = `Posted by User ID: ${post.user_id}`;
            postElement.appendChild(userIdElement);

            // Creaza și adauga elementul pentru continutul postarii
            const contentElement = document.createElement('p');
            contentElement.textContent = post.content;
            postElement.appendChild(contentElement);

            // Creaza și adauga elementul pentru numarul de likeuri
            const likesElement = document.createElement('span');
            likesElement.textContent = `Likes: ${post.like_count}`;
            likesElement.style.display = 'block';  // Asigură-te că like-urile sunt afișate pe o linie nouă
            postElement.appendChild(likesElement);

            // Creaza și adaugă elementul pentru data si ora postarii
            const timeElement = document.createElement('span');
            timeElement.textContent = `Posted on: ${new Date(post.time_post).toLocaleString()}`;
            timeElement.style.display = 'block';  // Asigură-te că data este afișată pe o linie nouă
            postElement.appendChild(timeElement);

            // Adauga postarea la containerul de postarii
            postsContainer.appendChild(postElement);
        });
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function loadViewUserNumbers(credential) {

    const endpoint = `/getNumbers/${credential}`;

    try {
        const response = await fetch(endpoint);
        if (response.ok) {
            const data = await response.json();
            const numbersSpan = document.getElementById('numbers');
            numbersSpan.innerHTML = `<span id="following" style="cursor: pointer;">Following: ${data.following}</span> | <span id="followers" style="cursor: pointer;">Followers: ${data.followers}</span>`;

            document.getElementById('following').addEventListener('click', () => {
                fetchAndShowListFollowing(credential);
            });
            document.getElementById('followers').addEventListener('click', () => {
                fetchAndShowListFollowers(credential);
            });

        } else {
            alert("eroare la load following/followers");
        }
    } catch (error) {
        console.error('Error:', error);
    }
}
async function fetchAndShowListFollowers(credential){
    try {
        const response = await fetch(`/followedByList/${credential}`);
        if (response.ok) {
            const usersList = await response.json();
            showModal(usersList);
        } else {
            const errorResponse = await response.text();
            alert(`Error loading followers list: ${errorResponse}`);
        }
    } catch (error) {
        console.error('Error fetching followers list:', error);
        alert('Failed to fetch followers list');
    }
}

async function fetchAndShowListFollowing(credential) {
    try {
        const response = await fetch(`/followingList/${credential}`);
        if (response.ok) {
            const usersList = await response.json();
            showModal(usersList);
        } else {
            const errorResponse = await response.text();
            alert(`Error loading followers list: ${errorResponse}`);
        }
    } catch (error) {
        console.error('Error fetching followers list:', error);
        alert('Failed to fetch followers list');
    }
}

function showModal(usersList) {
    // Crearea elementului modal
    const modal = document.createElement('div');
    modal.style.position = 'fixed';
    modal.style.left = '50%';
    modal.style.top = '50%';
    modal.style.transform = 'translate(-50%, -50%)';
    modal.style.backgroundColor = 'white';
    modal.style.padding = '20px';
    modal.style.border = '1px solid black';
    modal.style.zIndex = '1000';
    modal.style.width = '50%';
    modal.style.height = 'auto';
    modal.style.overflowY = 'auto';
    modal.style.maxHeight = '80%';

    // Titlu pentru modal
    const modalTitle = document.createElement('h2');
    modalTitle.textContent = 'List of Users';
    modal.appendChild(modalTitle);

    // Adaugarea feicarui utilizator în modal
    usersList.forEach(user => {
        const userItem = document.createElement('p');
        userItem.textContent = user;
        modal.appendChild(userItem);
    });

    // Buton de inchidere pentru modal
    const closeButton = document.createElement('button');
    closeButton.textContent = 'Close';
    closeButton.onclick = function() {
        document.body.removeChild(modal);
    };
    modal.appendChild(closeButton);

    //Adaugarea modalului la body si afisare
    document.body.appendChild(modal);
}


async function loadMyProfile() {
    const authType = sessionStorage.getItem("authType");
    const credential = sessionStorage.getItem("credential");
    try {
        var response;
        if (authType === "user")
            response = await fetch(`/view-profile-user/${credential}`);
        else if (authType === "email")
            response = await fetch(`/view-profile-email/${credential}`);
        else {
            alert('Invalid access. No user logged in');
            window.location.href = "../logIn.html";
            return;
        }
        const posts = await response.json();
        loadViewUserNumbers(credential);

        userIdHeading.textContent = "Your Profile"

        posts.forEach(post => {
            console.log("ceva");
            const postElement = document.createElement('div');
            postElement.classList.add('post');

            // Crează și adaugă elementul pentru ID-ul utilizatorului
            const userIdElement = document.createElement('h3');
            userIdElement.textContent = `Posted by User ID: ${post.user_id}`;
            postElement.appendChild(userIdElement);

            // Crează și adaugă elementul pentru conținutul postării
            const contentElement = document.createElement('p');
            contentElement.textContent = post.content;
            postElement.appendChild(contentElement);

            // Crează și adaugă elementul pentru numărul de like-uri
            const likesElement = document.createElement('span');
            likesElement.textContent = `Likes: ${post.like_count}`;
            likesElement.style.display = 'block';  // Asigură-te că like-urile sunt afișate pe o linie nouă
            postElement.appendChild(likesElement);

            // Crează și adaugă elementul pentru data și ora postării
            const timeElement = document.createElement('span');
            timeElement.textContent = `Posted on: ${new Date(post.time_post).toLocaleString()}`;
            timeElement.style.display = 'block';  // Asigură-te că data este afișată pe o linie nouă
            postElement.appendChild(timeElement);

            // Adaugă postarea la containerul de postări
            postsContainer.appendChild(postElement);
        });
    } catch (error) {
        console.error('Error fetching data:', error);
    }

}

async function onLoadButton() {
    const userId = sessionStorage.getItem("credential");
    const followingId = new URLSearchParams(window.location.search).get('userId');
    const followingEmail = new URLSearchParams(window.location.search).get('email');

    const followingCredential = followingId ? followingId : followingEmail;

    const buttonContainer = document.getElementById('followButtonContainer');
    const buttonElement = document.createElement('button');
    buttonElement.textContent = await getButtonData(userId, followingCredential);
    buttonElement.onclick = () => toggleFollow(userId, followingCredential, buttonElement)


    buttonContainer.appendChild(buttonElement);
}

async function getButtonData(userId, followingCredential) {
    const response = await fetch(`/isFollowing/${userId}/${followingCredential}`);
    if (response.ok) {
        const isFollowing = await response.json();
        return isFollowing ? 'Unfollow' : 'Follow';
    } else {
        console.error('Failed to determine follow status');
        return "???"; // Întoarce un text implicit în caz de eroare
    }
}

async function toggleFollow(userId, followingCredential, button) {
    const action = button.textContent === 'Follow' ? 'follow' : 'unfollow';
    const response = await fetch(`/${action}/${userId}/${followingCredential}`, {method: 'POST'});
    if (response.ok) {
        button.textContent = button.textContent === 'Follow' ? 'Unfollow' : 'Follow';
        await loadViewUserNumbers(followingCredential);
    } else {
        console.error('Failed to toggle follow status');
    }
}


function initializePage() {
    loadSerchProfile();
    onLoadButton();
}
