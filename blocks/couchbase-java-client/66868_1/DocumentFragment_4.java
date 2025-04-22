    private byte[] interpretResultRaw(SubdocOperationResult<OPERATION> result) {
        if (result.status() == ResponseStatus.FAILURE && result.value() instanceof RuntimeException) {
            throw (RuntimeException) result.value();
        } else if (result.value() instanceof CouchbaseException) {
            throw (CouchbaseException) result.value();
        } else {
            return result.rawValue();
        }
    }

