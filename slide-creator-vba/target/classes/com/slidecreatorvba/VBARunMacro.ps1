# Create a new instance of PowerPoint
$powerpoint = New-Object -ComObject PowerPoint.Application
# Create a new presentation
$presentation = $powerpoint.Presentations.Add()

# Wait a few seconds (you can increase or decrease the time depending on your needs)
Start-Sleep -Seconds 1

# Run the VBA file
$macroPath = "slide-creator-vba\src\main\java\com\slidecreatorvba\VBAScript.vba"  
if (Test-Path $macroPath) {
    $macro = Get-Content $macroPath -Raw -Encoding UTF8
    $presentation.VBProject.VBComponents.Add(1).CodeModule.AddFromString($macro)
    $presentation.Application.Run("CreatePresentationOnDesktop")  # Replace with the name of your macro
}

# Release COM resourcess
[System.Runtime.Interopservices.Marshal]::ReleaseComObject($presentation) | Out-Null
[System.Runtime.Interopservices.Marshal]::ReleaseComObject($powerpoint) | Out-Null