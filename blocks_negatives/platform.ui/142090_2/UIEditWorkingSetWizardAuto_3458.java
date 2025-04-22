        Text text = (Text) widgets.get(0);
        assertEquals(WORKING_SET_NAME_1, text.getText());
        assertTrue(page.canFlipToNextPage() == false);
        assertTrue(fWizard.canFinish() == false);
        assertNull(page.getErrorMessage());
        widgets = getWidgets((Composite) page.getControl(), Tree.class);
        Tree tree = (Tree) widgets.get(0);
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        assertEquals(workspace.getRoot().getProjects().length, tree
                .getItemCount());
        setTextWidgetText(WORKING_SET_NAME_2, page);
        assertTrue(fWizard.canFinish());
