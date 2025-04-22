			private void resetState() {
				currentIndex = NEEDS_INIT;// Reset
				removedListeners.clear();
				if (awaitingDecoration.isEmpty()) {
					resultCache.clear();
				}
			}
