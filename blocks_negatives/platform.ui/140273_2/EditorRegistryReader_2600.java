        editors.remove(editor);
        editors.add(0, editor);
        declaredDefaultEditors.remove(editor);
        declaredDefaultEditors.add(0, editor);
    }

    /**
     * Set the collection of all editors (EditorDescriptor)
     * registered for the file type described by this mapping.
     * Typically an editor is registered either through a plugin or explicitly by
     * the user modifying the associations in the preference pages.
     * This modifies the internal list to share the passed list.
     * (hence the clear indication of list in the method name)
     *
     * @param newEditors the new list of associated editors
     */
