            /**
             * Clear any cached information.
             */
            private void resetState() {
                removedListeners.clear();
                if (awaitingDecoration.isEmpty()) {
                    resultCache.clear();
                }
            }
