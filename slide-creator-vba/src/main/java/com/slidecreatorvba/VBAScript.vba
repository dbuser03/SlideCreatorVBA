Sub CreatePresentationOnDesktop()
    ' Variables declaration
    Dim pptApp As Object
    Dim pptPresentation As Object
    Dim desktopPath As String
    Dim presentationPath As String

    ' Obtain the desktop path
    desktopPath = CreateObject("WScript.Shell").SpecialFolders("Desktop")

    ' Open PowerPoint application
    Set pptApp = CreateObject("PowerPoint.Application")

    ' Create a new presentation
    Set pptPresentation = pptApp.Presentations.Add

    ' Create a new slide
    Set newSlide = pptPresentation.Slides.Add(1, 2)
       newSlide.Shapes(1).TextFrame.TextRange.InsertAfter "DURANTE LA SUA INFANZIA LECLERC HA AVUTO UNO"
       newSlide.Shapes(1).TextFrame.TextRange.Font.Size = 28
       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter "Durante la sua infanzia Leclerc ha avuto uno stretto rapporto di amicizia con lo scomparso pilota nizzardo Jules Bianchi, il quale era anche il suo padrino. Charles ha un fratello maggiore, Lorenzo, e uno minore, Arthur, anch'egli pilota per la Ferrari Dr"
       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter "iver Academy e militante nel Campionato FIA di Formula 2 2023. Leclerc ha iniziato la sua carriera nei kart nel 2005, vincendo il campionato PACA francese nello stesso anno, nel 2006 e nel 2008."
       newSlide.Shapes(2).TextFrame.TextRange.Font.Size = 14

    ' Create a new slide
    Set newSlide = pptPresentation.Slides.Add(1, 2)
       newSlide.Shapes(1).TextFrame.TextRange.InsertAfter "OLTRE AL FRANCESE, "
       newSlide.Shapes(1).TextFrame.TextRange.Font.Size = 28
       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter "Oltre al francese, sua lingua madre, parla fluentemente l'italiano e l'inglese. Da quando è in Formula 1 corre col 16 come numero di gara, giorno del suo compleanno oltreché somma del suo numero preferito, il 7, tuttavia già occupato da Kimi Räikkönen all"
       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter "'arrivo di Leclerc nel circus. Leclerc ha iniziato a correre con i kart nella pista gestita proprio dal padre di Bianchi, a Brignoles; come Bianchi, anche Leclerc si è poi unito alla compagnia di management ARM gestita da Nicolas Todt."
       newSlide.Shapes(2).TextFrame.TextRange.Font.Size = 14

    ' Create a new slide
    Set newSlide = pptPresentation.Slides.Add(1, 2)
       newSlide.Shapes(1).TextFrame.TextRange.InsertAfter "FIGLIO DI HERVÉ, "
       newSlide.Shapes(1).TextFrame.TextRange.Font.Size = 28
       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter "Figlio di Hervé, pilota di Formula 3 negli anni '80 e '90 del XX secolo, e morto prematuramente nel 2017 all'età di 54 anni dopo una lunga malattia, quattro giorni prima che Charles vincesse il Gran Premio d'Azerbaigian di Formula 2. Charles Marc Hervé Pe"
       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter "rceval Leclerc è un pilota automobilistico monegasco, attivo in Formula 1 con la Ferrari. Campione della GP3 2016 e della Formula 2 2017, ha fatto parte dal 2016 al 2018 della Ferrari Driver Academy e nel 2018 ha debuttato in Formula 1 per la Sauber, pass"
       newSlide.Shapes(2).TextFrame.TextRange.InsertAfter "ando dal 2019 in forza alla Scuderia Ferrari, con cui si è laureato vicecampione del mondo nel Campionato mondiale di Formula 1 2022."
       newSlide.Shapes(2).TextFrame.TextRange.Font.Size = 14
    
   ' Save the presentation
    presentationPath = desktopPath & "\" & "NuovaPresentazione.pptx"
    pptPresentation.SaveAs presentationPath

    ' Close the PowerPoint application
    pptApp.Quit

    ' Release the memory
    Set pptPresentation = Nothing
    Set pptApp = Nothing

End Sub
