        if (autoBuildButton.getSelection() != ResourcesPlugin.getWorkspace()
                .isAutoBuilding()) {
            try {
                description.setAutoBuilding(autoBuildButton.getSelection());
                ResourcesPlugin.getWorkspace().setDescription(description);
            } catch (CoreException e) {
                IDEWorkbenchPlugin.log(
                        "Error changing auto build workspace setting.", e//$NON-NLS-1$
                                .getStatus());
            }
        }
		if (maxSimultaneousBuilds.getIntValue() != description.getMaxConcurrentBuilds()) {
			try {
				description.setMaxConcurrentBuilds(maxSimultaneousBuilds.getIntValue());
				ResourcesPlugin.getWorkspace().setDescription(description);
			} catch (CoreException e) {
				IDEWorkbenchPlugin.log("Error changing max cucrrent builds workspace setting.", e//$NON-NLS-1$
						.getStatus());
			}
		}

