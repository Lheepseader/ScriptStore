    if (localStorage.getItem("sort_option") !== null) {
    console.log(localStorage.getItem("sort_option"));
    document.getElementById('sort_option').value = localStorage.getItem("sort_option");
    localStorage.clear();
}

const body = document.getElementById("sort_option");
const listOptions = ["JS", "HTML", "CSS"];
const listSelects = [];

function createSelect() {
    const option = document.createElement("option");
    // option.selected = ...;
    // option.disabled = ...;
    option.value = listOptions[i];
    option.text = listOptions[i];
    select.add(option);
}