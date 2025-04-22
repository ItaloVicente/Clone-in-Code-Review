        IRunnableWithProgress runnable = monitor -> {

            try {
		IProjectDescription description = project.getDescription();
		description.setReferencedProjects(refs);
		project.setDescription(description, monitor);
            } catch (CoreException e) {
		throw new InvocationTargetException(e);
