				if (file.isDirectory()) {
					Repository sub = ResourceUtil.getRepository(path);
					if (sub != null && sub != repository) {
						RevCommit headCommit = Activator.getDefault()
								.getRepositoryUtil().parseHeadCommit(sub);
						if (headCommit == null) {
							return null;
						}
						return new ByteArrayInputStream(Constants
								.encode(headCommit.name()));
					}
				}
