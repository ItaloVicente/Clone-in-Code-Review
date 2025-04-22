				}
				projectHolder[0] = project;
				return BuildUtilities.isEnabled(projectHolder, IncrementalProjectBuilder.CLEAN_BUILD);
			}
		});
		projectNames.setInput(ResourcesPlugin.getWorkspace().getRoot());
