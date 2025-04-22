						RepositoryConfig config = node.getRepository()
								.getConfig();
						config.unset("remote", configName, "pushurl"); //$NON-NLS-1$ //$NON-NLS-2$
						config.unset("remote", configName, "push"); //$NON-NLS-1$ //$NON-NLS-2$
						try {
							config.save();
