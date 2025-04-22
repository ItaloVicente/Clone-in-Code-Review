        if (opts.ingestMethod == IngestMethod.REPLACE && opts.idGenerator.equals(DEFAULT_ID_GENERATOR)) {
            throw new IllegalArgumentException("IngestMethod.REPLACE does not with the default ID generator " +
                    "which only creates new UUIDs and will make every replace operation fail. Please create " +
                    "your own ID Generator!");
        }

