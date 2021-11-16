// fetches all possible game genres
const fetchGenres = async() => {
    // the connection endpoint options to fetch all genres
    const fetchGenresOptions = {
        method: 'GET',
        url: 'http://localhost:8082/all/genres'
    };

    // empty list initially
    var data = [];
    // OK status initially
    var status = 200;

    // sends the request and waits for a response
    await axios.request(fetchGenresOptions).then(response => {
      data = response.data;
      // if BAD REQUEST or NOT FOUND
    }).catch(function (error) {
        // updates the status
        status = error.response.status;
        // shows the full error in the console
        console.error(error);
    });
  
    return {data, status};
}

// fetches only news that correspond to the specified genre 
// (use only allowed values for genre, e.g. items from output of fetchGenres)
const fetchNews = async(genre) => {
    const fetchNewsOptions = {
        method: 'GET',
        url: 'http://localhost:8082/news/filter',
        // sets the genre parameter to the user selection(supplied as function argument)
        params: {'genre': genre}
    };

    // empty list initially
    var data = [];
    // OK status initially
    var status = 200;

    // sends the request and waits for a response
    await axios.request(fetchNewsOptions).then(response => {
        // populates the list with data received from request
        data = response.data;
        // IF BAD REQUEST or NOT FOUND
      }).catch(function (error) {
          // updates the status
          status = error.response.status;
          // shows the full error in the console
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
            var titleHeader = document.createElement("h3");
            titleHeader.innerText = "Apologies, it appears that the game list of MMO BOMB database did not specify any genres!"
            document.getElementById('genres').appendChild(titleHeader);
        } else {
            var titleHeader = document.createElement("h3");
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
            var titleHeader = document.createElement("h3");
            titleHeader.innerText = "Apologies, there are no game news at the moment for genre: " + genre;
            document.getElementById('news').appendChild(titleHeader);
        } else {
            var titleHeader = document.createElement("h3");
            titleHeader.innerText = "Oops! Something went wrong when connecting to our servers. Make sure you have a stable Internet connection or try again later.";
            document.getElementById('news').appendChild(titleHeader);
        }
    }
    
    // for each news in the list
    for (let i=0; i < news.length; i++) {
        // defines HTML element containing the news title
        title = news[i].title;
        var titleHeader = document.createElement("h3");
        titleHeader.innerText = title;

        // defines HTML element containing the news image
        imageUrl = news[i].main_image;
        var img = new Image();
        img.src = imageUrl;

        // defines HTML element containing short description of the news
        articleContent = news[i].article_content;
        var descriptionP = document.createElement("div");
        descriptionP.innerHTML = articleContent;

        // appends to the specified element with id='news' in the order:
        // TITLE followed by IMAGE followed by DESCRIPTION
        document.getElementById('news').appendChild(titleHeader);
        document.getElementById('news').appendChild(img);
        document.getElementById('news').appendChild(descriptionP);
    }
}

// change functionality as needed to render the giveaways accordingly
const renderGiveaways = async(genre) => {
    response = await fetchGiveaways(genre);
    var giveaways = response.data;
    var status = response.status;
    // handles NOT FOUND and BAD REQUEST errors by displaying useful message to users
    if (giveaways.length === 0) {
        if (status === 404) {
            var titleHeader = document.createElement("h3");
            titleHeader.innerText = "Apologies, there are no giveaways at the moment for genre: " + genre;
            document.getElementById('giveaways').appendChild(titleHeader);
        } else {
            var titleHeader = document.createElement("h3");
            titleHeader.innerText = "Oops! Something went wrong when connecting to our servers. Make sure you have a stable Internet connection or try again later.";
            document.getElementById('giveaways').appendChild(titleHeader);
        }
    }
    
    for (let i=0; i < giveaways.length; i++) {
        // defines HTML element containing the giveaway title
        title = giveaways[i].title;
        var titleHeader = document.createElement("h3");
        titleHeader.innerText = title;

        // defines HTML element containing the giveaway image
        imageUrl = giveaways[i].main_image;
        var img = new Image();
        img.src = imageUrl;

        // defines HTML element containing short description of the giveaway
        shortDescription = giveaways[i].short_description;
        var descriptionP = document.createElement("p");
        descriptionP.innerText = shortDescription;

        // define HTML elements to state how many keys are left of that giveaway
        remainingKeysHeader = document.createElement("h4");
        remainingKeysHeader.innerText = "Keys remaining: " + giveaways[i].keys_left;

        // appends to the specified element with id='giveaways' in the order:
        // TITLE followed by IMAGE followed by DESCRIPTION
        document.getElementById('giveaways').appendChild(titleHeader);
        document.getElementById('giveaways').appendChild(img);
        document.getElementById('giveaways').appendChild(descriptionP);
        document.getElementById('giveaways').appendChild(remainingKeysHeader);
    }
}

// fetches steam data for a game via name
const fetchSteam = async(name) => {
    // the connection endpoint to fetch all genres
    const fetchSteamOptions = {
        method: 'GET',
        url: 'http://localhost:7777/steam/'+name
    };

    var data = [];
    var status = 200;

    // sends the request and waits for a response
    await axios.request(fetchSteamOptions).then(response => {
      data = response.data;
    }).catch(function (error) {
        status = error.response.status;
        console.error(error);
    });
    //TODO: NOT FOUND functionality
  
    return {data, status};
}

// fetches metacritic data for a game via name
const fetchMetacritic = async(name) => {
    // the connection endpoint to fetch all genres
    const fetchMetacriticOptions = {
        method: 'GET',
        url: 'http://localhost:7777/metacritic/'+name
    };

    var data = [];
    var status = 200;

    // sends the request and waits for a response
    await axios.request(fetchMetacriticOptions).then(response => {
      data = response.data;
    }).catch(function (error) {
        status = error.response.status;
        console.error(error);
    });
    //TODO: NOT FOUND functionality
  
    return {data, status};
}

// fetches client reviews for a game via name
const fetchClient = async(name) => {
    // the connection endpoint to fetch all genres
    const fetchClientOptions = {
        method: 'GET',
        url: 'http://localhost:7777/client/'+name
    };

    var data = [];
    var status = 200;

    // sends the request and waits for a response
    await axios.request(fetchClientOptions).then(response => {
      data = response.data;
    }).catch(function (error) {
        status = error.response.status;
        console.error(error);
    });
    //TODO: NOT FOUND functionality
  
    return {data, status};
}

// posts a client review by game name
// fetches client reviews for a game via name
const postClient = async(name) => {
    // the connection endpoint to fetch all genres
    const postClientOptions = {
        method: 'POST',
        url: 'http://localhost:7777/client/'+name,
        args: {"user_id": user_id,"name": name,"comment": comment,"score": score,"date": date}
    };

    var data = [];
    var status = 200;

    // sends the request and waits for a response
    await axios.post(postClientOptions).then(response => {
      data = response.data;
    }).catch(function (error) {
        status = error.response.status;
        console.error(error);
    });
    //TODO: NOT FOUND functionality
  
    return {data, status};
}

const renderGame = async(name) => {
    steamData = await fetchSteam(name);
    metacriticData = await fetchMetacritic(name);
    clientData = await fetchClient(name);

    document.getElementById("gameName").innerText = name;
    document.getElementById("desc").innerText = metacriticData["summary"];
    document.getElementById("price").innerText = steamData["original_price"];
    document.getElementById("tags").innerText = steamData["popular_tags"];
    
    //document.getElementById("gameName").innerText = 
    
    return 0;


}