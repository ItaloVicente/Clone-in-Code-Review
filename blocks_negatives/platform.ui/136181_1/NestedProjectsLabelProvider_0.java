							if (severities != null && !severities.isDone()) {
								try {
									severities.cancel(true);
								} catch (CancellationException ex) {
								}
								severities = null;
								return false;
							}
							dirtyResources.add(markerDelta.getResource());
