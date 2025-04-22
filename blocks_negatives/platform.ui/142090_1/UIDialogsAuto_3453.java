        DialogCheck.assertDialogTexts(dialog);
    }

    public void testCopyMoveProject() {
        IProject dummyProject = ResourcesPlugin.getWorkspace().getRoot()
                .getProject("DummyProject");
        Dialog dialog = new ProjectLocationSelectionDialog(getShell(),
                dummyProject);
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testCopyMoveResource() {
        Dialog dialog = new ContainerSelectionDialog(getShell(), null, true,
                "Copy Resources");
        DialogCheck.assertDialogTexts(dialog);
    }

    public void testEditActionSetsDialog() {
        /*
         * current perspective, or default; currently only //gets first
         * perspective in the registry. persp = new
         * Perspective((PerspectiveDescriptor)getWorkbench().getPerspectiveRegistry().getPerspectives()[0],
         * (WorkbenchPage)getWorkbench().getActiveWorkbenchWindow().getActivePage() );
         * dialog = new CustomizePerspectiveDialog(getShell(), persp); } catch
         * (WorkbenchException e) { dialog = null; }
         * DialogCheck.assertDialogTexts(dialog); if (persp != null) {
         * persp.dispose(); }
         */
    }

    public void testEditorSelection() {
        Dialog dialog = new EditorSelectionDialog(getShell());
        DialogCheck.assertDialogTexts(dialog);
    }

    /**
     * 1GJWD2E: ITPUI:ALL - Test classes should not be released in public
     * packages. public void testFindReplace() { Dialog dialog =
     * TextEditorTestStub.newFindReplaceDialog( getShell() );
     * DialogCheck.assertDialogTexts(dialog); } public void
     * testGotoResource() { Dialog dialog =
     * NavigatorTestStub.newGotoResourceDialog(getShell(), new IResource[0]);
     * DialogCheck.assertDialogTexts(dialog); }
     */
    public void testNavigatorFilter() {
