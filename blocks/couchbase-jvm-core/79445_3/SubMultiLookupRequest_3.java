        if (docOptionsBuilder.accessDeleted()) {
            this.docFlags |= SUBDOC_DOCFLAG_ACCESS_DELETED;
        }
    }

    public SubMultiLookupRequest(String key, String bucket, LookupCommand... commands) {
        this(key, bucket, SubMultiLookupDocOptionsBuilder.builder(), commands);
