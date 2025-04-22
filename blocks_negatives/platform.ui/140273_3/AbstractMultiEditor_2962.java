        for (IEditorPart e : innerEditors) {
            e.doSave(monitor);
        }
    }

    /*
     * @see IEditorPart#doSaveAs()
     */
    @Override
