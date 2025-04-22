    /**
     * WelcomeEditorInput constructor comment.
     */
    public WelcomeEditorInput(AboutInfo info) {
        super();
        if (info == null) {
            throw new IllegalArgumentException();
        }
        aboutInfo = info;
    }
