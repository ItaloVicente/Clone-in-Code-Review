        try {
            DesignDocument stored = ctx.bucketManager().getDesignDocument("cities");
            if (!stored.equals(designDoc)) {
                ctx.bucketManager().upsertDesignDocument(designDoc);
            }
        } catch (DesignDocumentDoesNotExistException ex) {
