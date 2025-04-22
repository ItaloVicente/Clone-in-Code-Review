        IRunnableWithProgress runnable = new IRunnableWithProgress() {
            @Override
			public void run(IProgressMonitor monitor)
                    throws InvocationTargetException {

                try {
                    IProjectDescription description = project.getDescription();
                    description.setReferencedProjects(refs);
                    project.setDescription(description, monitor);
                } catch (CoreException e) {
                    throw new InvocationTargetException(e);
                }
