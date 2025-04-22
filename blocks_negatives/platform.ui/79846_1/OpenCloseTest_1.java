            FileUtil.createProject("TestProject");
            IProject testProject = ResourcesPlugin.getWorkspace().getRoot()
            FileUtil.createFile("tempFile.txt", testProject);
            testProject.open(null);
            IEditorInput editorInput = new FileEditorInput(testProject
                    .getFile("tempFile.txt"));
            IEditorPart editorPart = null;
            for (index = 0; index < numIterations; index++) {
                editorPart = page.openEditor(editorInput,
                page.closeEditor(editorPart, false);
            }
