				String filePath = getRepoRelativePath(handledFile);
				if (filePath == null) {
					IResourceVariant sourceVariant = subscriber
							.getSourceTree().getResourceVariant(handledFile);
					IResourceVariant remoteVariant = subscriber.getRemoteTree()
							.getResourceVariant(handledFile);
					IResourceVariant baseVariant = subscriber.getBaseTree()
							.getResourceVariant(handledFile);
					if (sourceVariant instanceof AbstractGitResourceVariant) {
						String sourcePath = ((AbstractGitResourceVariant) sourceVariant)
								.getAbsolutePath();
						if (new File(sourcePath).exists()) {
							filePath = ((AbstractGitResourceVariant) sourceVariant)
									.getPath();
						}
					}
					if (filePath == null
							&& remoteVariant instanceof AbstractGitResourceVariant) {
						String remotePath = ((AbstractGitResourceVariant) remoteVariant)
								.getAbsolutePath();
						if (new File(remotePath).exists()) {
							filePath = ((AbstractGitResourceVariant) remoteVariant)
									.getPath();
						}
					}
					if (filePath == null
							&& baseVariant instanceof AbstractGitResourceVariant) {
						String basePath = ((AbstractGitResourceVariant) baseVariant)
								.getAbsolutePath();
						if (new File(basePath).exists()) {
							filePath = ((AbstractGitResourceVariant) baseVariant)
									.getPath();
						}
					}

				}
