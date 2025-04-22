						List<RefSpec> fetchRefSpecs = remoteConfig
								.getFetchRefSpecs();
						for (RefSpec refSpec : fetchRefSpecs) {
							if (refSpec.matchSource(remoteBranchName)) {
								RefSpec expandFromSource = refSpec
										.expandFromSource(remoteBranchName);
								ref = getRef(expandFromSource.getDestination());
								break;
							}
						}
