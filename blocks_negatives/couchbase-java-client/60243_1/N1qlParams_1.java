            throw new UnsupportedOperationException(
                "Document ID fallback for AT_PLUS not yet supported. ID: " + id);
        } else if (token.bucket() != bucket.name()) {
            throw new IllegalArgumentException(
                "The given MutationToken does not correspond to the Bucket scope! Token: "
                    + token.bucket() + ", Bucket: " + bucket.name());
