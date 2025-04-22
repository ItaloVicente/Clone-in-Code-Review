					if (time.equals("upstream")) {
						if (name == null)
							name = new String(revChars
						if (name.equals(""))
							name = Constants.HEAD;
						Ref ref = getRef(name);
						if (ref == null)
							return null;
						if (ref.isSymbolic())
							ref = ref.getLeaf();
						name = ref.getName();

						RemoteConfig remoteConfig;
						try {
							remoteConfig = new RemoteConfig(getConfig()
									"origin");
						} catch (URISyntaxException e) {
							throw new RevisionSyntaxException(revstr);
						}
						String remoteBranchName = getConfig()
								.getString(
										ConfigConstants.CONFIG_BRANCH_SECTION
								Repository.shortenRefName(ref.getName())
										ConfigConstants.CONFIG_KEY_MERGE);
						List<RefSpec> fetchRefSpecs = remoteConfig
								.getFetchRefSpecs();
						for (RefSpec refSpec : fetchRefSpecs) {
							if (refSpec.matchSource(remoteBranchName)) {
								RefSpec expandFromSource = refSpec
										.expandFromSource(remoteBranchName);
								name = expandFromSource.getDestination();
								break;
							}
						}
						if (name == null)
							throw new RevisionSyntaxException(revstr);
					} else if (time.matches("^-\\d+$")) {
