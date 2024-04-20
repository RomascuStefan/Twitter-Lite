async function getMainPagePosts() {
    const postsContainer = document.getElementById('postsContainer');
    const authType = sessionStorage.getItem("authType");
    const credential = sessionStorage.getItem("credential");

    try {
        var response;
        if (authType === "user")
            response = await fetch(`/main-page-view-id/${credential}`);
        else if (authType === "email")
            response = await fetch(`/main-page-view-email/${credential}`);
        else {
            alert('Invalid access. No user logged in');
            window.location.href = "../logIn.html";
            return;
        }
        const posts = await response.json();

        for (const post of posts) {
            const postElement = document.createElement('div');
            postElement.id = `post-${post.id}`;
            postElement.classList.add('post');

            const userIdElement = document.createElement('h3');
            userIdElement.textContent = `Posted by User ID: ${post.user_id}`;
            postElement.appendChild(userIdElement);

            const contentElement = document.createElement('p');
            contentElement.textContent = post.content;
            postElement.appendChild(contentElement);

            const likesElement = document.createElement('span');
            likesElement.id = `likeElement-${post.id}`;
            likesElement.textContent = `Likes: ${post.like_count}`;
            likesElement.style.display = 'block';
            postElement.appendChild(likesElement);

            const timeElement = document.createElement('span');
            timeElement.textContent = `Posted on: ${new Date(post.time_post).toLocaleString()}`;
            timeElement.style.display = 'block';
            postElement.appendChild(timeElement);

            const buttonElement = document.createElement('button');
            buttonElement.id = `buttonElement-${post.id}`;
            buttonElement.textContent = await getNumeButon(post.id);
            buttonElement.onclick = async function () {
                await likeUnlikePost(post.id, credential)();
                fetchAndUpdateLikes(post.id)();
            };
            buttonElement.style.display = 'block';
            postElement.appendChild(buttonElement);

            postsContainer.appendChild(postElement);
        }
    } catch (error) {
        console.error('Error fetching main page posts:', error);
    }
}

function likeUnlikePost(postId, credential) {
    return async function () {
        const result = await fetch(`/likeUnlikePost/${credential}/${postId}`, {
            method: 'POST'
        });
        if (result.ok) {
            const buttonText = await getNumeButon(postId);
            const buttonElement = document.getElementById(`buttonElement-${postId}`);
            buttonElement.textContent = buttonText;
        } else {
            console.error('Failed to toggle like status');
        }
    };
}

async function getNumeButon(postId) {
    const credential = sessionStorage.getItem("credential");
    try {
        const response = await fetch(`/isPostLiked/${credential}/${postId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch like status');
        }
        const isLiked = await response.json();
        return isLiked ? "Unlike" : "Like";
    } catch (error) {
        console.error('Error fetching like status:', error);
        return 'ERROR';
    }
}

function fetchAndUpdateLikes(postId) {
    return async function () {
        const response = await fetch(`/getPostLikes/${postId}`);
        if (response.ok) {
            const likesData = await response.json();
            const likesElement = document.getElementById(`likeElement-${postId}`);
            likesElement.textContent = `Likes: ${likesData.value}`;
        } else {
            console.error('Failed to fetch likes count');
        }
    };
}


