			try {
				IProjectDescription description = project.getDescription();
				description.setNatureIds(ProjectNaturesPage.this.naturesIdsWorkingCopy
						.toArray(new String[ProjectNaturesPage.this.naturesIdsWorkingCopy.size()]));
				project.setDescription(description, monitor);
			} catch (CoreException e) {
				throw new InvocationTargetException(e);
