        subscriber.awaitTerminalEvent();
        List<DCPRequest> items = subscriber.getOnNextEvents();

        boolean seenMutation = false;
        boolean seenSnapshot = false;
        for (DCPRequest found : items) {
            if (found instanceof SnapshotMarkerMessage) {
                seenSnapshot = true;
            } else if (found instanceof MutationMessage) {
                seenMutation = true;
                assertEquals("foo", ((MutationMessage) found).key());
            }
        }
