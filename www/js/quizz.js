let pokemonActuel = '';
let questionActuelle = 0;
const totalQuestions = 10;
let score = 0;

function getRandomNumber(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

async function fetchPokemon() {
    if (questionActuelle >= totalQuestions) {
        saveScoreToFile(score);
        return;
    }

    const randomId = getRandomNumber(1, 1025);
    const url = `https://tyradex.vercel.app/api/v1/pokemon/${randomId}`;

    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error(`Erreur HTTP ! Statut : ${response.status}`);
        }
        const data = await response.json();
        console.log("Pokémon récupéré :", data);

        const pokemonName = data.name.fr;
        pokemonActuel = pokemonName.toLowerCase();
        const pokemonImage = data.sprites.regular;

        document.getElementById("pokemonName").textContent = pokemonName;
        document.getElementById("pokemonImage").src = pokemonImage;

        const btn = document.getElementById("validerBtn");
        btn.disabled = false;

    } catch (error) {
        console.error("Erreur lors de la récupération du Pokémon :", error);
    }
}

function validerPokemon(){
    const btn = document.getElementById("validerBtn");
    btn.disabled = true;

    const pokemonSaisi = document.getElementById("pokemonSaisi").value.toLowerCase();
    const pastilles = document.querySelectorAll(".pastille");

    if(pokemonSaisi === pokemonActuel){
        pastilles[questionActuelle].style.backgroundColor = "green";
        score++;
    }
    else{
        pastilles[questionActuelle].style.backgroundColor = "red";
    }

    questionActuelle++;
    document.getElementById("pokemonSaisi").value = "";
    fetchPokemon();
}

function saveScoreToFile(score) {
    window.resolveLocalFileSystemURL(cordova.file.dataDirectory, function (dirEntry) {
        dirEntry.getFile("scores.txt", { create: true }, function (fileEntry) {
            fileEntry.createWriter(function (fileWriter) {
                fileWriter.seek(fileWriter.length);
                fileWriter.onwriteend = function() {
                    console.log("Écriture terminée avec succès");
                    window.location.href = "./classement.html";
                };
                fileWriter.onerror = function(e) {
                    console.error("Erreur d'écriture :", e);
                };
                const blob = new Blob([score + "\n"], { type: "text/plain" });
                fileWriter.write(blob);
                console.log("Score sauvegardé :", score);
            });
        });
    });
}

document.addEventListener("deviceready", fetchPokemon);