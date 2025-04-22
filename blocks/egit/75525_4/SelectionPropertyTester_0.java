					if ((resource instanceof IFolder)
							&& resource.equals(m.getContainer())) {
						return false;
					} else {
						return testRepositoryProperties(m.getRepository(),
								args);
					}
