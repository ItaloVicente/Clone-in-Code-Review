        return asyncBucket
            .getFromReplica(document, type)
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
