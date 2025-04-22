
	private static class IndexCommand {

		private final RepositoryMapping mapping;

		private AddCommand addCommand;

		private RmCommand rmCommand;

		public IndexCommand(final RepositoryMapping mapping) {
			this.mapping = mapping;
		}

		private String getFilePattern(final IResource resource) {
			String pattern = mapping.getRepoRelativePath(resource);
			return "".equals(pattern) ? "." : pattern; //$NON-NLS-1$ //$NON-NLS-2$
		}

		public void add(final IResource resource) {
			if (resource.exists()) {
				if (addCommand == null) {
					Repository repo = mapping.getRepository();
					AdaptableFileTreeIterator it = new AdaptableFileTreeIterator(
							repo, resource.getWorkspace().getRoot());
					addCommand = new Git(repo).add().setWorkingTreeIterator(it);
				}
				addCommand.addFilepattern(getFilePattern(resource));
			} else {
				if (rmCommand == null)
					rmCommand = new Git(mapping.getRepository()).rm();
				rmCommand.addFilepattern(getFilePattern(resource));
			}
		}

		public void call() throws NoFilepatternException {
			if (addCommand != null)
				addCommand.call();
			if (rmCommand != null)
				rmCommand.call();
		}
	}

