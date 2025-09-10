document.addEventListener("deviceready", () => {
    window.resolveLocalFileSystemURL(cordova.file.dataDirectory, (dir) => {
        dir.getFile("scores.txt", { create: true }, (file) => {
            file.file((fileObj) => {
                const reader = new FileReader();
                reader.onloadend = () => {
                    const contenu = reader.result.trim();
                    document.getElementById("scoreAffiche").textContent =
                        contenu || "Aucun score enregistré.";
                };
                reader.readAsText(fileObj);
            });
        });
    });
});
