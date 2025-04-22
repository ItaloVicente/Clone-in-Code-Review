        return asyncClusterManager
            .getBuckets()
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
