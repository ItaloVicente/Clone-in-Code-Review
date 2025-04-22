	@NonNull
	public static Repository[] getAllRepositories(
			@Nullable IEvaluationContext evaluationContext) {
		return getAllRepositories(getSelection(evaluationContext));
	}

	@NonNull
	public static Repository[] getAllRepositories(
			@NonNull IStructuredSelection selection) {
		if (selection.isEmpty()) {
			return new Repository[0];
		}
		Set<Object> elements = getSelectionContents(selection);
		Set<Repository> repos = new LinkedHashSet<>();
		for (Object location : elements) {
			Repository repo = null;
			if (location instanceof Repository) {
				repo = (Repository) location;
			} else if (location instanceof IResource) {
				repo = ResourceUtil.getRepository((IResource) location);
			} else if (location instanceof IPath) {
				repo = ResourceUtil.getRepository((IPath) location);
			} else {
				repo = Adapters.adapt(location, Repository.class);
			}
			if (repo != null) {
				repos.add(repo);
			} else {
				return new Repository[0];
			}
		}
		return repos.toArray(new Repository[0]);
	}

