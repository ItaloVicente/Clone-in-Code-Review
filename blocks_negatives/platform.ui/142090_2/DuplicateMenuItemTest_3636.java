        IStructuredSelection selection = new StructuredSelection(testProject);
        checkSelection(selection);
        selection = new StructuredSelection(testFolder);
        checkSelection(selection);
        selection = new StructuredSelection(testFile);
        checkSelection(selection);
    }
