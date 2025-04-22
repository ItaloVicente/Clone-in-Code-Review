        if (fragments == null || fragments.length == 0) {
            throw new IllegalArgumentException("At least one DocumentFragment needs to be provided.");
        }

        for (DocumentFragment doc : fragments) {
            storeToken(doc.mutationToken());
        }

        return this;
