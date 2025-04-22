					for (File supportedDirectory : supportedDirectories) {
						if (supportedDirectory.isDirectory()) {
							if (!res.containsKey(supportedDirectory)) {
								res.put(supportedDirectory, new ArrayList<ProjectConfigurator>());
							}
							res.get(supportedDirectory).add(configurator);
						} else {
							IDEWorkbenchPlugin.log("Project detection must return only directories.\n" //$NON-NLS-1$
									+ supportedDirectory + " is not a directory.\nContributed by " //$NON-NLS-1$
									+ configurator.getClass().getName());
