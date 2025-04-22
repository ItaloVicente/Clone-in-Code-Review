				asyncUpdateRunnable = () -> {
					if (knownElements == null) {
						return;
					}
					asyncUpdatePending = false;
					if (realizedElements != null) {
						realizedElements.addAll(knownElements);
