# My Chess

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Swing](https://img.shields.io/badge/Java%20Swing-Desktop%20UI-1f6feb?style=for-the-badge)
![AWT](https://img.shields.io/badge/AWT-Graphics-0ea5e9?style=for-the-badge)
![CheerpJ](https://img.shields.io/badge/CheerpJ-Java%20in%20Browser-2563eb?style=for-the-badge)
![HTML5](https://img.shields.io/badge/HTML5-Page%20Host-e34f26?style=for-the-badge&logo=html5&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-Loader-f7df1e?style=for-the-badge&logo=javascript&logoColor=black)
![CSS3](https://img.shields.io/badge/CSS3-Styling-1572b6?style=for-the-badge&logo=css3&logoColor=white)
![Python](https://img.shields.io/badge/Python-Local%20Server-3776ab?style=for-the-badge&logo=python&logoColor=white)
![Vercel](https://img.shields.io/badge/Vercel-Hosting-black?style=for-the-badge&logo=vercel&logoColor=white)
![PowerShell](https://img.shields.io/badge/PowerShell-Build%20Script-5391fe?style=for-the-badge&logo=powershell&logoColor=white)

This is my chess project built in Java. I originally made it as a normal Swing desktop app, then I adapted it so it can also run inside a browser.

## How The Project Works (Simple Version)

The main game logic is in Java classes:

- `src/Main/GamePanel.java` handles the game loop, rendering, and turns.
- `src/piece/*.java` contains move rules for each piece.
- `res/piece/*.png` are the chess piece images.

When I build the project, all `.class` files and image assets are packed into one file: `mychessapp.jar`.

## How Java Runs In The Browser

Browsers do not run Java Swing directly. So the trick is:

1. I run the Java game as a JAR.
2. `web/index.html` loads the CheerpJ runtime script.
3. CheerpJ creates a display canvas in the page.
4. CheerpJ launches `mychessapp.jar` in that canvas.

So basically, I did not rewrite the game in JavaScript. I kept the Java code, and CheerpJ acts like a browser-side Java runtime.

## Run Locally In Browser

1. Build the web jar:

```powershell
Set-Location "c:\Users\Matthew Amiadamen\saol\Projects\mychessapp\chessapp"
powershell -ExecutionPolicy Bypass -File .\scripts\build-web.ps1
```

2. Start local server from the `web` folder:

```powershell
Set-Location "c:\Users\Matthew Amiadamen\saol\Projects\mychessapp\chessapp\web"
python -m http.server 8080
```

3. Open:

```text
http://localhost:8080
```

## Deploy To Vercel

1. Rebuild first (this creates `web/mychessapp.jar`):

```powershell
Set-Location "c:\Users\Matthew Amiadamen\saol\Projects\mychessapp\chessapp"
powershell -ExecutionPolicy Bypass -File .\scripts\build-web.ps1
```

2. Deploy:

```powershell
npm i -g vercel
vercel
```

3. Publish production link:

```powershell
vercel --prod
```

`vercel.json` points deployment output to the `web` folder, so it serves both `index.html` and `mychessapp.jar`.

Important: commit and push `web/mychessapp.jar` whenever your Java code changes, otherwise online deploys will not have the updated game.

## Quick Notes

- CheerpJ loader URL is `https://cjrtnc.leaningtech.com/4.2/loader.js`.
- `web/mychessapp.jar` must stay next to `web/index.html`.
- CheerpJ licensing terms apply for non-personal or non-evaluation use.
