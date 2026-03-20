Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
$classesDir = Join-Path $projectRoot "build\classes"
$distDir = Join-Path $projectRoot "dist"
$webDir = Join-Path $projectRoot "web"
$jarPath = Join-Path $distDir "mychessapp.jar"

if (Test-Path $classesDir) {
    Remove-Item -Recurse -Force $classesDir
}
if (Test-Path $jarPath) {
    Remove-Item -Force $jarPath
}

New-Item -ItemType Directory -Path $classesDir -Force | Out-Null
New-Item -ItemType Directory -Path $distDir -Force | Out-Null

Push-Location $projectRoot
try {
    # CheerpJ is more reliable with older classfile targets.
    javac --release 8 -d "build/classes" src/Main/*.java src/piece/*.java
    Copy-Item -Path "res/piece/*.png" -Destination "build/classes/piece/" -Force
    jar cfe "dist/mychessapp.jar" Main.Main -C "build/classes" .

    Copy-Item -Path $jarPath -Destination (Join-Path $webDir "mychessapp.jar") -Force
    Write-Host "Built and copied jar to web/mychessapp.jar"
}
finally {
    Pop-Location
}