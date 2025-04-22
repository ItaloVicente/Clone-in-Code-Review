        private EditorInputData(String editorId, IEditorInput input) {
            this.editorId = editorId;
            this.input = input;
        }
    }

    /**
     * Creates a new transfer object.
     */
    private EditorInputTransfer() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static EditorInputTransfer getInstance() {
        return instance;
    }

    @Override
