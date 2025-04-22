							if (refName.equals("")) {
								ref = getRef(Constants.HEAD);
								if (ref == null)
									return null;
								if (ref.isSymbolic())
									ref = ref.getLeaf();
								if (ref.getObjectId() == null)
									return null;
							} else {
								ref = getRef(refName);
								if (ref == null)
									throw new RevisionSyntaxException(revstr);
							}
						}
						RemoteConfig remoteConfig;
						try {
							remoteConfig = new RemoteConfig(getConfig()
									"origin");
						} catch (URISyntaxException e) {
							throw new RevisionSyntaxException(revstr);
