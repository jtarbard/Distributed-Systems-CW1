// fetches all possible game genres
const fetchGenres = async() => {
    // the connection endpoint to fetch all genres
    const fetchGenresOptions = {
        method: 'GET',
        url: 'http://localhost:8082/all/genres'
    };

    var data = [];
    var status = 200;

    // sends the request and waits for a response
    await axios.request(fetchGenresOptions).then(response => {
      data = response.data;
    }).catch(function (error) {
        status = error.response.status;
        console.error(error);
    });
    //TODO: NOT FOUND functionality
  
    return {data, status};
}

// fetches only news that correspond to the specified genre 
// (use only allowed values for genre, e.g. items in output of fetchGenres)
const fetchNews = async(genre) => {
    const fetchNewsOptions = {
        method: 'GET',
        url: 'http://localhost:8082/news/filter',
        params: {'genre': genre}
    };

    // empty list initially
    var data = [];
    // OK status initially
    var status = 200;

    await axios.request(fetchNewsOptions).then(response => {
        // populate the list with data received from request
        data = response.data;
      }).catch(function (error) {
          // change the status to the server response
          status = error.response.status;
          console.error(error);
      });

    return {data,status};
}

// change functionaity as needed to render the genres accordingly
// change div id, target class and style as you find appropriate
const renderGenres = async() => {
    // fetches all possible genres
    response = await fetchGenres();
    var genres = response.data;
    var status = response.status;
    // handles NOT FOUND and BAD REQUEST errors by displaying useful message to users
    if (genres.length === 0) {
        if (status === 404) {
            var titleHeader = document.createElement("h3")
            titleHeader.innerText = "Apologies, it appears that the game list of MMO BOMB database did not specify any genres!"
            document.getElementById('genres').appendChild(titleHeader);
        } else {
            var titleHeader = document.createElement("h3")
            titleHeader.innerText = "Oops! Something went wrong when connecting to our servers. Make sure you have a stable Internet connection or try again later.";
            document.getElementById('genres').appendChild(titleHeader);
        }
    }

    // from the list of all genres
    for (let i=0; i < genres.length; i++) {
        var divItem = document.createElement("div");

        // create a header element
        var genreLink = document.createElement("a");
        // with text corresponding to the respective genre
        genreLink.innerText = genres[i];
        // add the functionality to the button
        genreLink.onclick = function () {
            sessionStorage.setItem("selectedGenre",genres[i])
            window.location.href = "news.html";
        }

        divItem.appendChild(genreLink);

        // append to the HTML document, at id genres
        document.getElementById('genres').appendChild(divItem);
    }
}

// change functionality as needed to render the news accordingly
const renderNews = async(genre) => {
    response = await fetchNews(genre);
    var news = response.data;
    var status = response.status;
    // handles NOT FOUND and BAD REQUEST errors by displaying useful message to users
    if (news.length === 0) {
        if (status === 404) {
            var titleHeader = document.createElement("h3")
            titleHeader.innerText = "Apologies, there are no game news at the moment for genre: " + genre;
            document.getElementById('news').appendChild(titleHeader);
        } else {
            var titleHeader = document.createElement("h3")
            titleHeader.innerText = "Oops! Something went wrong when connecting to our servers. Make sure you have a stable Internet connection or try again later.";
            document.getElementById('news').appendChild(titleHeader);
        }
    }
    
    // for each news in the list
    for (let i=0; i < news.length; i++) {
        // defines HTML element containing the news title
        title = news[i].title;
        var titleHeader = document.createElement("h3")
        titleHeader.innerText = title;

        // defines HTML element containing the news image
        imageUrl = news[i].main_image;
        var img = new Image();
        img.src = imageUrl;

        // defines HTML element containing short description of the news
        shortDescription = news[i].short_description;
        var descriptionP = document.createElement("p");
        descriptionP.innerText = shortDescription;

        // appends to the specified element with id='news' in the order:
        // TITLE followed by IMAGE followed by DESCRIPTION
        document.getElementById('news').appendChild(titleHeader);
        document.getElementById('news').appendChild(img);
        document.getElementById('news').appendChild(descriptionP);
    }
}