		SubMonitor subMonitor = SubMonitor.convert(monitor, 101);
		subMonitor.setTaskName(CoreText.RepositoryFinder_finding);
		if (loc == null) {
			throw new CoreException(Activator.error(
					NLS.bind(CoreText.RepositoryFinder_ResourceDoesNotExist, c),
					null));
		}
		final File fsLoc = loc.toFile();
		assert fsLoc.isAbsolute();
