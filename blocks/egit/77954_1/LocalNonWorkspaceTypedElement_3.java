				if (file.isDirectory()) {
					IPath repoRelative = ResourceUtil
							.getRepositoryRelativePath(path, repository);
					if (repoRelative != null) {
						Repository sub = ResourceUtil.getRepository(path);
						RevCommit headCommit = Activator.getDefault()
								.getRepositoryUtil().parseHeadCommit(sub);
						return new ByteArrayInputStream(Constants
								.encode(headCommit.name()));
					}
				}
