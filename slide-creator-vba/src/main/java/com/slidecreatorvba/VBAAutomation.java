package com.slidecreatorvba;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The VBAAutomation class, which handles the creation of the VBA code.
 * 
 * Uses the default constructor.
*/

public class VBAAutomation {
    
    private final String VBAString1 = 
        "Sub CreatePresentationOnDesktop()\n" +
        "    ' Variables declaration\n" +
        "    Dim pptApp As Object\n" +
        "    Dim pptPresentation As Object\n" +
        "    Dim desktopPath As String\n" +
        "    Dim presentationPath As String\n" +
        "\n" +
        "    ' Obtain the desktop path\n" +
        "    desktopPath = CreateObject(\"WScript.Shell\").SpecialFolders(\"Desktop\")\n" +
        "\n" +
        "    ' Open PowerPoint application\n" +
        "    Set pptApp = CreateObject(\"PowerPoint.Application\")\n" +
        "\n" +
        "    ' Create a new presentation\n" +
        "    Set pptPresentation = pptApp.Presentations.Add\n";

    private final String VBAString2 = 
        "    \n   ' Save the presentation\n" +
        "    presentationPath = desktopPath & \"\\\" & \"NuovaPresentazione.pptx\"\n" +
        "    pptPresentation.SaveAs presentationPath\n" +
        "\n" +
        "    ' Close the PowerPoint application\n" +
        "    pptApp.Quit\n" +
        "\n" +
        "    ' Release the memory\n" +
        "    Set pptPresentation = Nothing\n" +
        "    Set pptApp = Nothing\n" +
        "\n" +
        "End Sub\n";

    private String VBAoutputString = VBAString1;

    /**
     * Default constructor for the VBAAutomation class.
     * Initializes the VBAoutputString to VBAString1.
     * 
     * @param ppt The PowerPoint object.
    */

    public void completeVBA (PowerPoint ppt) {
        for (int i = ppt.getNumberOfSlides() - 1; i >= 0 ; i--){
            // Add the slides to the PowerPoint
            VBAoutputString += "\n    ' Create a new slide\n" +
            "    Set newSlide = pptPresentation.Slides.Add(1, 2)\n";
    
            String title = ppt.getSlide(i).getTitle().replace("\"", "\"\"");
            while (title.length() > 255) {
                String subText = title.substring(0, 255);
                title = title.substring(255);
                VBAoutputString += "       newSlide.Shapes(1).TextFrame.TextRange.InsertAfter \"" + subText + "\"\n";
            }
            VBAoutputString += "       newSlide.Shapes(1).TextFrame.TextRange.InsertAfter \"" + title + "\"\n" +
            "       newSlide.Shapes(1).TextFrame.TextRange.Font.Size = 28\n";
    
            String slideText = ppt.getSlide(i).getText().replace("\"", "\"\"");
            while (slideText.length() > 255) {
                String subText = slideText.substring(0, 255);
                slideText = slideText.substring(255);
                VBAoutputString += "       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter \"" + subText + "\"\n";
            }
            VBAoutputString += "       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter \"" + slideText + "\"\n" +
            "       newSlide.Shapes(2).TextFrame.TextRange.Font.Size = 14\n";
        }
    
        VBAoutputString += VBAString2;
    
        // Write the VBA code to a file
        try {
            System.out.println("Writing VBA code to file...");
            // Insert the relative path to the VBA file
            Path path = Paths.get("slide-creator-vba\\src\\main\\java\\com\\slidecreatorvba\\VBAScript.vba");
            // Write the VBA code to the file
            Files.write(path, VBAoutputString.getBytes());
            System.out.println("VBA code written to file.");
        } catch (Exception e) {
            System.out.println("Error writing VBA code to file.");
            e.printStackTrace();
        }
    }
}