        } finally {
            event.setRequest(null);
            if (endOfBatch && nodes != null) {
                for (Node node : nodes) {
                    node.send(SignalFlush.INSTANCE);
                }
            }
