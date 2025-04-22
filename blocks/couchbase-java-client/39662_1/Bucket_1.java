    Observable<DesignDocument> getDesignDocument(String name);
    Observable<DesignDocument> listDesignDocuments();
    Observable<DesignDocument> insertDesignDocument(DesignDocument designDocument);
    Observable<DesignDocument> updateDesignDocument(DesignDocument designDocument);

    Observable<Boolean> removeDesignDocument(DesignDocument designDocument);
    Observable<Boolean> removeDesignDocument(String name);
    Observable<Boolean> removeDesignDocument(String name, boolean development);
