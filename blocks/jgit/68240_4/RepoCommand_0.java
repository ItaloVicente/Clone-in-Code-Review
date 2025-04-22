						if (objectId == null) {
							if (ignoreRemoteFailures) {
								continue;
							}
							throw new RemoteUnavailableException(nameUri);
						}
						if (recordRemoteBranch && objectId != null) {
