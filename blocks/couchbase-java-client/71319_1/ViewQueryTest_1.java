        try {
            DesignDocument stored = ctx.bucketManager().getDesignDocument("users");
            if (!stored.equals(designDoc)) {
                ctx.bucketManager().upsertDesignDocument(designDoc);
            }
        } catch (DesignDocumentDoesNotExistException ex) {
