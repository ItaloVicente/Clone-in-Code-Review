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
