    }

    /**
     * Returns the name of the given editor
     *
     * @param editor
     * @return
     */
    public static String getName(IEditorPart editor) {
        IWorkbenchPage page = editor.getSite().getPage();
        IWorkbenchPartReference ref = page.getReference(editor);
        return ref.getPartName();
    }
