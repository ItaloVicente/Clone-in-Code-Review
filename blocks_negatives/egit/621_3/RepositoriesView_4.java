						RepositoryConfig config = node.getRepository()
								.getConfig();
						config.unset("remote", configName, "url"); //$NON-NLS-1$ //$NON-NLS-2$
						config.unset("remote", configName, "fetch"); //$NON-NLS-1$//$NON-NLS-2$
						try {
							config.save();
