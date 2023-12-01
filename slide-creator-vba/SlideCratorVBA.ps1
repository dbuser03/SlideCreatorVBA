# PowerShell script to run a JAR file

# Path to the JAR file
$jarFile = "slide-creator-vba\target\slide-creator-vba-1.0-SNAPSHOT.jar"

Set-Location "C:\Users\DANIELE BUSER\Desktop\SlideCreatorVBA"

# Print the absolute path to the JAR file
Write-Host "Running JAR file at: $jarFileAbsolutePath"

# Run the JAR file
java -jar $jarFile

# Wait 2 seconds
Start-Sleep -s 15