    boolean isPersistable() {
        if (editorInput != null) {
            IPersistableElement persistable = editorInput.getPersistable();
            return persistable != null;
        }
        return memento != null;
    }
