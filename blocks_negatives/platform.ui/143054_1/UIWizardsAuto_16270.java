                super.addPages();
                IWizardPage page = getPage("newFilePage1");
                assertTrue("Expected newFilePage1",
                        page instanceof WizardNewFileCreationPage);
                WizardNewFileCreationPage fileCreationPage = (WizardNewFileCreationPage) page;

                try {
                    project = FileUtil.createProject("testNewFile2");
                } catch (CoreException e) {
                    fail(e.getMessage());
                }
                fileCreationPage.setContainerFullPath(project.getFullPath());
                fileCreationPage.setFileName("testFileName.test");
            }
        };

        wizard.init(PlatformUI.getWorkbench(), new StructuredSelection());
        wizard.setNeedsProgressMonitor(true);
        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setText("CreateFileAction_title");
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_FILE_WIZARD);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNewFolder() {
        BasicNewFolderResourceWizard wizard = new BasicNewFolderResourceWizard();
        wizard.init(PlatformUI.getWorkbench(), new StructuredSelection());
        wizard.setNeedsProgressMonitor(true);
        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setText("CreateFolderAction_title");
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_FOLDER_WIZARD);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNewProjectPage1() {
        BasicNewProjectResourceWizard wizard = new BasicNewProjectResourceWizard();
        wizard.init(PlatformUI.getWorkbench(), null);
        wizard.setNeedsProgressMonitor(true);

        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x),
                SIZING_WIZARD_HEIGHT_2);
        dialog.getShell().setText("CreateFileAction_title");
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_PROJECT_WIZARD);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNewProjectPage2() {
        BasicNewProjectResourceWizard wizard = new BasicNewProjectResourceWizard();
        wizard.init(PlatformUI.getWorkbench(), null);
        wizard.setNeedsProgressMonitor(true);

        WizardNewProjectReferencePage page = new WizardNewProjectReferencePage(
        page.setTitle(ResourceMessages.NewProject_referenceTitle);
        page.setDescription(ResourceMessages.NewProject_referenceDescription);
        page.setWizard(wizard);

        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x),
                SIZING_WIZARD_HEIGHT_2);
        dialog.getShell().setText("CreateFileAction_title");
        dialog.showPage(page);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_PROJECT_WIZARD);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNewProject() {
        NewWizard wizard = new NewWizard();
        wizard.setProjectsOnly(true);
        initNewWizard(wizard);

        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x),
                SIZING_WIZARD_HEIGHT_2);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_PROJECT_WIZARD);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testNewResource() {
        NewWizard wizard = new NewWizard();
        initNewWizard(wizard);

        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH_2, dialog.getShell().getSize().x),
                SIZING_WIZARD_HEIGHT_2);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(), IWorkbenchHelpContextIds.NEW_WIZARD);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testWizardWindowTitle() {

        checkWizardWindowTitle(null);

    }
