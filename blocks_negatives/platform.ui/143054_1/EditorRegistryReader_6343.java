    /**
     *  Create an instance of this class.
     *
     *  @param name java.lang.String
     *  @param extension java.lang.String
     */
    public FileEditorMapping(String name, String extension) {
        super();
        if (name == null || name.length() < 1) {
            setName(STAR);
        } else {
