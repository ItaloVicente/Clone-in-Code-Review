						try {
							objectId = callback.sha1(
									nameUri
						} catch (GitAPIException e) {
							if (ignoreRemoteFailures) {
								LOG.warn("Failed to resolve SHA1."
								continue;
							} else {
								throw e;
							}
						}
						if (recordRemoteBranch && objectId != null) {
