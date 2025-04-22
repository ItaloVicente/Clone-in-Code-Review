					for (IResource resource : resources) {


						if (!Team.isIgnoredHint(resource)) {
							addIgnore(monitor, resource);
						}
						monitor.worked(1);
					}
					monitor.done();
