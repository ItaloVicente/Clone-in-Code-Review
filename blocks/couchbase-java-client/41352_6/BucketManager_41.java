    DesignDocument upsertDesignDocument(DesignDocument designDocument, long timeout, TimeUnit timeUnit);

    DesignDocument upsertDesignDocument(DesignDocument designDocument, boolean development);

    DesignDocument upsertDesignDocument(DesignDocument designDocument, boolean development, long timeout, TimeUnit timeUnit);

    Boolean removeDesignDocument(String name);

    Boolean removeDesignDocument(String name, long timeout, TimeUnit timeUnit);

    Boolean removeDesignDocument(String name, boolean development);

    Boolean removeDesignDocument(String name, boolean development, long timeout, TimeUnit timeUnit);

    DesignDocument publishDesignDocument(String name);

    DesignDocument publishDesignDocument(String name, long timeout, TimeUnit timeUnit);

    DesignDocument publishDesignDocument(String name, boolean overwrite);

