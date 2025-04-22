	private void importProjects(final Repository repository,
			final IWorkingSet[] sets) {
		String repoName = Activator.getDefault().getRepositoryUtil()
				.getRepositoryName(repository);
		Job importJob = new Job(MessageFormat.format(
				UIText.GitCloneWizard_jobImportProjects, repoName)) {

			protected IStatus run(IProgressMonitor monitor) {
				List<File> files = new ArrayList<File>();
				ProjectUtil.findProjectFiles(files, repository.getWorkTree(),
						null, monitor);
				if (files.isEmpty())
					return Status.OK_STATUS;

				Set<ProjectRecord> records = new LinkedHashSet<ProjectRecord>();
				for (File file : files)
					records.add(new ProjectRecord(file));
				try {
					ProjectUtils.createProjects(records, repository, sets,
							monitor);
				} catch (InvocationTargetException e) {
					Activator.logError(e.getLocalizedMessage(), e);
				} catch (InterruptedException e) {
					Activator.logError(e.getLocalizedMessage(), e);
				}
				return Status.OK_STATUS;
			}
		};
		importJob.schedule();
	}

