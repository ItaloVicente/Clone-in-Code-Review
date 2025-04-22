				if (!(element instanceof IProject)) {
					return false;
				}
				IProject project = (IProject) element;
				if (!project.isAccessible()) {
					return false;
				}
				projectHolder[0] = project;
				return BuildUtilities.isEnabled(projectHolder, IncrementalProjectBuilder.CLEAN_BUILD);
			}
		});
		treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
		treeViewer.setCheckedElements(selection);
		Object[] checked = treeViewer.getCheckedElements();
