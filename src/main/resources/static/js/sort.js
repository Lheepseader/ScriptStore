const body = document.getElementById('sort');
const listOptions = ["id", "title", "datePublication", "duration"];
const listText = ["по умолчанию", "по названию", "по дате", "по длительности"];
const next = document.getElementById('switches-next');
const prev = document.getElementById('switches-prev');

const search = document.getElementById('doSearch');
var val = document.getElementById('searchInput').value;

let url = new URL(window.location);



const maxPage = document.getElementById('maxPage').textContent - 1;


if ((+url.searchParams.get("page") === 0) || (!url.searchParams.has("page"))) {
    prev.style.display = "none";
} else {
    prev.style.display = "block"
}

if (+url.searchParams.get("page") === +maxPage) {
    next.style.display = "none";
} else {
    next.style.display = "block";
}


window.onload = function() {
    const newSelect = document.createElement("select");
    newSelect.id = "sort_option";
    newSelect.name = "select";
    body.appendChild(newSelect);


    for (let i = 0; i < listOptions.length; i++) {
        const option = document.createElement("option");
        option.value = listOptions[i];
        option.text = listText[i];
        if (localStorage.getItem('sort_option') === listOptions[i]) {
            option.selected = true;
        }
        newSelect.add(option);
   }

};


const selectDiv = document.getElementById('sort');

selectDiv.addEventListener('change', function(){
  var sort_option = document.getElementById('sort_option').value;
  localStorage.setItem('sort_option', sort_option);
  url.searchParams.set("sort_option", sort_option);
  window.location = url;
});



next.addEventListener('click', function(){
  if (url.searchParams.has("page")) {
    url.searchParams.set("page", +url.searchParams.get("page") + 1);
  } else {
    url.searchParams.append("page", 1);
  }
  window.location = url;
});

prev.addEventListener('click', function(){
  if (+url.searchParams.get("page") > 0) {
    url.searchParams.set("page", +url.searchParams.get("page") - 1);
  }
  window.location = url;
});

doSearch.addEventListener('click', function(){
    if (url.searchParams.has("search")) {
        url.searchParams.set("search", val);
    } else {
        url.searchParams.append("search", val);
    }

    window.location = url;
});