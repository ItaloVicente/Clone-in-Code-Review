        oldMinMaxState = apiStore.getBoolean(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX);

        apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, false);

        try {
            file1 = FileUtil.createFile("Test1.txt", project); //$NON-NLS-1$
            file2 = FileUtil.createFile("Test2.txt", project); //$NON-NLS-1$
            file3 = FileUtil.createFile("Test3.txt", project); //$NON-NLS-1$
            editor1 = page.openEditor(new FileEditorInput(file1),
                    MockEditorPart.ID1);
            editor2 = page.openEditor(new FileEditorInput(file2),
                    MockEditorPart.ID2);
            editor3 = page.openEditor(new FileEditorInput(file3),
                    MockEditorPart.ID2);
