    Observable<DesignDocument> getDesignDocument(String name);
    Observable<DesignDocument> getDesignDocument(String name, boolean development);

    Observable<DesignDocument> listDesignDocuments();
    Observable<DesignDocument> insertDesignDocument(DesignDocument designDocument);
    Observable<DesignDocument> replaceDesignDocument(DesignDocument designDocument);

    Observable<Boolean> removeDesignDocument(String name);
    Observable<Boolean> removeDesignDocument(String name, boolean development);
