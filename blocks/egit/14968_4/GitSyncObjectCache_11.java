						.entrySet()) {
					String entryKey = entry.getKey();
					if (!value.members.containsKey(entryKey)) {
						GitSyncObjectCache entryValue = entry.getValue();
						if (updateRequests.contains(parentPath + "/" //$NON-NLS-1$
								+ entryValue.diffEntry))
							entryValue.diffEntry.changeType = ChangeType.IN_SYNC;
					}
				}
