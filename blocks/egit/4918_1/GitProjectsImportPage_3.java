			if(element instanceof ProjectFolder) {
				ProjectFolder projectFolder = (ProjectFolder) element;
				boolean hasValidProjects = hasValidProjects(projectFolder);
				if(!hasValidProjects) {
					return PlatformUI.getWorkbench().getDisplay().getSystemColor(
							SWT.COLOR_GRAY);
				} else {
					return null;
				}
			}

