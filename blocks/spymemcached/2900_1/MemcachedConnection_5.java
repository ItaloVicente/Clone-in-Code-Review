        for (MemcachedNode qa : nodesToShutdown) {
            if (!addedQueue.contains(qa)) {
                nodesToShutdown.remove(qa);
                Collection<Operation> notCompletedOperations = qa.destroyInputQueue();
                if (qa.getChannel() != null) {
                    qa.getChannel().close();
                    qa.setSk(null);
                    if (qa.getBytesRemainingToWrite() > 0) {
                        getLogger().warn(
                                "Shut down with %d bytes remaining to write",
                                qa.getBytesRemainingToWrite());
                    }
                    getLogger().debug("Shut down channel %s", qa.getChannel());
                }
                redistributeOperations(notCompletedOperations);
            }
        }
