							throw new RemoteUnavailableException(nameUri);
						}
						if (recordRemoteBranch) {
							cfg.setString("submodule", path, "branch", //$NON-NLS-1$ //$NON-NLS-2$
									proj.getRevision());
						}
