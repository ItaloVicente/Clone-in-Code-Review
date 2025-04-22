        IFile file = getFileResource();
        if (file == null) {
            return;
        }
        try {
        	if (openUsingDescriptor) {
        		((WorkbenchPage) page).openEditorFromDescriptor(new FileEditorInput(file), editorDescriptor, true, null);
        	} else {
	            String editorId = editorDescriptor == null ? IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID
	                    : editorDescriptor.getId();

	            page.openEditor(new FileEditorInput(file), editorId, true, MATCH_BOTH);
	            IDE.setDefaultEditor(file, editorId);
        	}
        } catch (PartInitException e) {
            DialogUtil.openError(page.getWorkbenchWindow().getShell(),
                    IDEWorkbenchMessages.OpenWithMenu_dialogTitle,
                    e.getMessage(), e);
        }
    }

    /**
