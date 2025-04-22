    public void createDocument(boolean createDocument) {
        if (this.insertDocument && createDocument) {
            throw new IllegalArgumentException("Invalid to set createDocument to true along with insertDocument");
        }
        this.createDocument = createDocument; }

    @Override
    public boolean insertDocument() { return this.insertDocument; }

    public void insertDocument(boolean insertDocument) {
        if (this.createDocument && insertDocument) {
            throw new IllegalArgumentException("Invalid to set insertDocument to true along with createDocument");
        }
        this.insertDocument = insertDocument;
    }
