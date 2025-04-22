        try {
            boolean activate = OpenStrategy.activateOnOpen();
            if (editorDescriptor == null) {
                IDE.openEditor(getWorkbenchPage(), file, activate);
            } else {
                if (ensureFileLocal(file)) {
                    getWorkbenchPage().openEditor(new FileEditorInput(file),
                            editorDescriptor.getId(), activate);
                }
            }
        } catch (PartInitException e) {
            DialogUtil.openError(getWorkbenchPage().getWorkbenchWindow()
                    .getShell(), IDEWorkbenchMessages.OpenFileAction_openFileShellTitle,
                    e.getMessage(), e);
        }
    }
