				boolean useWorkingTree = !conflicting || useWorkspace;
				if (!useWorkingTree && conflicting && dirCacheEntry != null) {
					useWorkingTree = !Instant.EPOCH
							.equals(dirCacheEntry.getLastModifiedInstant());
				}
				if (useWorkingTree) {
					boolean useOursFilter = conflicting && useOurs;
					int conflictMarkerSize = 7; // Git default
					if (useOursFilter) {
						Attributes attributes = tw.getAttributes();
						useOursFilter = attributes.canBeContentMerged();
						if (useOursFilter) {
							Attribute markerSize = attributes
									.get("conflict-marker-size"); //$NON-NLS-1$
							if (markerSize != null && Attribute.State.CUSTOM
									.equals(markerSize.getState())) {
								try {
									conflictMarkerSize = Integer
											.parseUnsignedInt(
													markerSize.getValue());
								} catch (NumberFormatException e) {
								}
							}
						}
