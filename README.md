# SlideCreatorVBA

SlideCreatorVBA is a free software tool that automates the creation of PowerPoint presentations from text script files. It utilizes a hybrid text summarization technique that combines TextRank and frequency analysis to extract key points from the script. This approach ensures that the generated slides capture the essential information while-1.0-SNAPSHOTtaining conciseness.

## Features
* **Advanced Text Summarization:** Employing a hybrid TextRank and frequency analysis method, the software summarizes the text script, ensuring concise and impactful presentations.
* **Automated Slide Generation:** SlideCreatorVBA allows you to generate simple title-text PowerPoint slides automatically.

## Installation
* **Download the SlideCreatorVBA ZIP file:** Visit the SlideCreatorVBA GitHub repository and navigate to the "Releases" section. Download the latest ZIP file.
* **Extract the ZIP file:** Extract the downloaded ZIP file to a location on your computer where you want to keep the SlideCreatorVBA files. The extracted folder will be named "SlideCreatorVBA-1.0-SNAPSHOT" and this is going to be the folder structure.

    ```
    SlideCreatorVBA-1.0-SNAPSHOT
    │
    └───SlideCreatorVBA-1.0-SNAPSHOT
        |
        ├───SlideCreatorVBA.lnk
        ├───README.txt
        │
        └───slide-creator-vba
            │
            ├───.vscode
            │
            ├───src
            │
            └───target
    ```
* **Remove the outer folder:** As you can see from the tree structure when you extract the zip file Windows auto create an outer SlideCreatorVBA-1.0-SNAPSHOT folder. o run the software, you first have to move the inner folder out of the outer folder.

    ```
    SlideCreatorVBA-1.0-SNAPSHOT
    |
    ├───SlideCratorVBA.lnk
    ├───README.txt
    │
    └───slide-creator-vba
        │
        ├───.vscode
        │
        ├───src
        │
        └───target
    ```
* **Modify the shortcut:** To run the software you have to double click the shortcut but before that make sure that the short cut is pointing to the right PowerShell script.

    - Open the slide-creator-vba folder and copy SlideCreatorVBA.ps1 as a path
    - Go back to SlideCreatorVBA-1.0-SNAPSHOT folder and right click the shortcut with the star icon
    - Go to the properties of this shortcut and edit the 'Target' field

    ``` powershell.exe -ExecutionPolicy Bypass -File "add the path you copied at point 1" ```
    - Before running the software you also have to edit the 'Start in' field as follow

    ``` "add the path you copied at point 1 but make sure to delete \SlideCratorVBA.ps1" ```
    - Open the script located here
    
    ``` SlideCreatorVBA-1.0-SNAPSHOT\slide-creator-vba\SlideCratorVBA.ps1 ``` 
    
    and change ```Set-Location "absolute path to the SlideCreatorVBA-1.0-SNAPSHOT folder "```

* **You are now ready to run SlideCreatorVBA**

## Usage
The usage of this open source software is pretty straightforward. Once you are able to run the .ps1 script, it will open a terminal window with all the instructions.

* **Create a .txt script:** If you want to make a Charles Leclerc presentation just create a simple .txt file with all the informations (you can also copy from [Wikipedia](https://it.wikipedia.org/wiki/Pagina_principale)).
* **Copy your .txt file path:** Copy your .txt file path and paste it in the program.
* **Insert the desired number of slides:** Insert the desired length of the presentation and it will be created right on your desktop.

## Support
This is my first open source project if you want to help improving this software just contact me at [buserdaniele@gmail.com](mailto:buserdaniele@gmail.com) and make sure to add the project name in the object section of your email.

* If you have any doubt regarding the code please check the Javadoc of this project located in the following folder.

    ```
    SlideCreatorVBA-1.0-SNAPSHOT
    └── slide-creator-vba
        └── target
            └── site
                └── apidocs
                    └── allpackages-index.html
    ```

## Roadmap
- [ ] Modify the FileInputHandler class to allow the user to use a Word or PDF file an input (needs to use some libraries).
- [ ] Implement Stemming/Lemmatization for both TextRank and FrequencyRank algorithms (needs to use some libraries).
- [ ] Implement TF-IDF for both TextRank and FrequencyRank algorithms (needs to use some libraries).
- [ ] Utilize this techniques also to improve the title extraction method
- [ ] Improve portability and installation process for this software

## Contributions
I have never had anyone contributing to my projects except for my close friends if you are skilled in any language, you think you have a good idea or you think you can bring value to this project in general plase [contact me](mailto:buserdaniele@gmail.com).

## License
MIT