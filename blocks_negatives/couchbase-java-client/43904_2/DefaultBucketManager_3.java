        return asyncBucketManager
            .getDesignDocuments()
            .timeout(timeout, timeUnit)
            .toList()
            .toBlocking()
            .single();
