async function getOneUser(id) {
    let url = "adminApi/oneUser/" + id;
    let response = await fetch(url);
    return await response.json();
}