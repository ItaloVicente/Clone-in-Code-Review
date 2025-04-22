			protected void execute(IProgressMonitor monitor)
				throws CoreException {
				try {
					monitor.beginTask("Creating New Project", 3000);//$NON-NLS-1$

					monitor.worked(1000);
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					final IProjectDescription description = workspace
						.newProjectDescription(newProjectHandle.getName());
					description.setLocation(newProjectPath);
