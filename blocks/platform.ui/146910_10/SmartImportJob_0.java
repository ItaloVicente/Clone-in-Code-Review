			} else if (rebuildProjectsAfterImport) {
				this.report.keySet().forEach(project -> {
					try {
						project.build(IncrementalProjectBuilder.CLEAN_BUILD, monitor);
						if (!isAutoBuilding) {
							project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
						}
					} catch (CoreException e) {
						listener.errorHappened(project.getLocation(), e);
					}
				});
