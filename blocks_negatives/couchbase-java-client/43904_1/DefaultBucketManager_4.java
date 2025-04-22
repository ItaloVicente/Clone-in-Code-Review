        return asyncBucketManager
            .getDesignDocuments(development)
            .timeout(timeout, timeUnit)
            .toList()
            .toBlocking()
            .single();
