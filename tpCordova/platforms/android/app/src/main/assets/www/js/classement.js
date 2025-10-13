function readScoresFromFile() {
    window.resolveLocalFileSystemURL(cordova.file.dataDirectory, function (dirEntry) {
        dirEntry.getFile("scores.txt", { create: true }, function (fileEntry) {
            fileEntry.file(function (file) {
                const reader = new FileReader();
                reader.onloadend = function () {
                    const content = this.result.trim();
                    console.log("Contenu brut du fichier :", JSON.stringify(content));

                    if (content === "") {
                        console.log("Fichier vide");
                        afficherClassement([]);
                        return;
                    }

                    const scores = content.split("\n")
                        .filter(line => line.trim() !== "")
                        .map(line => parseInt(line.trim(), 10))
                        .filter(score => !isNaN(score));

                    console.log("scores : " + scores);

                    const topScores = scores.sort((a, b) => b - a).slice(0, 5);

                    afficherClassement(topScores);
                };
                reader.readAsText(file);
            });
        });
    });
}

function afficherClassement(scores) {
    const container = document.getElementById("scoreAffiche");
    if (!scores || scores.length === 0) {
        container.textContent = "Pas encore de score enregistré.";
        return;
    }

    let html = `
        <table class="score-table">
            <thead>
                <tr>
                    <th>Rang</th>
                    <th>Score</th>
                </tr>
            </thead>
            <tbody>
    `;

    scores.forEach((score, index) => {
        html += `
            <tr>
                <td>${index + 1}</td>
                <td>${score}/10</td>
            </tr>
        `;
    });

    html += `
            </tbody>
        </table>
    `;

    container.innerHTML = html;
}

document.addEventListener("deviceready", readScoresFromFile);
