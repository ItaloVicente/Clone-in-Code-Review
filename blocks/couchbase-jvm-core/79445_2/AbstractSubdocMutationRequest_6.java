    public void createDocument(boolean createDocument) {
        if (this.addDocument && createDocument) {
            throw new IllegalArgumentException("Invalid to set createDocument to true along with addDocument");
        }
        this.createDocument = createDocument; }

    @Override
    public boolean addDocument() { return this.addDocument; }

    public void addDocument(boolean addDocument) {
        if (this.createDocument && addDocument) {
            throw new IllegalArgumentException("Invalid to set addDocument to true along with createDocument");
        }
        this.addDocument = addDocument;
    }
