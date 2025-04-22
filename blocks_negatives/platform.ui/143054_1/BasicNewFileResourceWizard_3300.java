        super.addPages();
        mainPage = new WizardNewFileCreationPage("newFilePage1", getSelection());//$NON-NLS-1$
        mainPage.setTitle(ResourceMessages.FileResource_pageTitle);
        mainPage.setDescription(ResourceMessages.FileResource_description);
        addPage(mainPage);
    }
