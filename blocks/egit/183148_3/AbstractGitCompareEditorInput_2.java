				GitInfo info = Adapters.adapt(element, GitInfo.class);
				if (info != null && info.getRepository() != null) {
					IPath path = Path.fromPortableString(info.getGitPath());
					if (!repository.isBare()) {
						File f = new File(repository.getWorkTree(),
								path.toOSString());
						if (f.exists()) {
							return getShowInSource(new ShowInContext(this,
									new StructuredSelection(Path.fromOSString(
											f.getAbsolutePath()))));
						}
					}
