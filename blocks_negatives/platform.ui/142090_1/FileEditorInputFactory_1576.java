    /**
     * Saves the state of the given file editor input into the given memento.
     *
     * @param memento the storage area for element state
     * @param input the file editor input
     */
    public static void saveState(IMemento memento, FileEditorInput input) {
        IFile file = input.getFile();
        memento.putString(TAG_PATH, file.getFullPath().toString());
    }
