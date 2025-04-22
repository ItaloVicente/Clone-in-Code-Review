					if (path.isAbsolute()) {
						return getShowInSource(new ShowInContext(this,
								new StructuredSelection(path)));
					} else {
						GitInfo info = getGitInfo(path);
						if (info != null) {
							return getShowInSource(new ShowInContext(this,
									new StructuredSelection(info)));
						}
					}
