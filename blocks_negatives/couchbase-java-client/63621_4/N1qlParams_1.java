        if (documents == null || documents.length == 0) {
            throw new IllegalArgumentException("At least one Document needs to be provided.");
        }

        for (Document doc : documents) {
            storeToken(doc.mutationToken());
        }

        return this;
