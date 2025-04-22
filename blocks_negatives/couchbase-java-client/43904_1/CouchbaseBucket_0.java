        return asyncBucket
            .getFromReplica(id, type)
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
