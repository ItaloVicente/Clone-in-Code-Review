		if (sb.charAt(sb.length() - 1) == '\n')
			sb.setLength(sb.length() - 1);

		patchContent = sb.toString();
	}

	private IProject getProject(final DiffEntry ent) {
		Side side = ent.getChangeType() == ChangeType.ADD ? Side.NEW : Side.OLD;
		String path = ent.getPath(side);
		return getProject(path);
	}

	private IProject getProject(String path) {
		URI pathUri = repository.getWorkTree().toURI().resolve(path);
		IFile[] files = ResourcesPlugin.getWorkspace().getRoot()
				.findFilesForLocationURI(pathUri);
		Assert.isLegal(files.length == 1, NLS.bind(CoreText.CreatePatchOperation_couldNotFindProject, path, repository));
		return files[0].getProject();
