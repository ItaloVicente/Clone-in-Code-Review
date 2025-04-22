				asyncUpdateRunnable = new Runnable() {
					@Override
					public void run() {
						if (knownElements == null) {
							return;
						}
						asyncUpdatePending = false;
						if (realizedElements != null) {
							realizedElements.addAll(knownElements);
						}
