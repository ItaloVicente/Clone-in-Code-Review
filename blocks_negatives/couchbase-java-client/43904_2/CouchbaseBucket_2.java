        return asyncBucket
            .getFromReplica(id, type, target)
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
