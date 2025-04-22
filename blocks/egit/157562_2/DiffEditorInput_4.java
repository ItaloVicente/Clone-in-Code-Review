	public DiffEditorInput(@NonNull IRepositoryCommit tip,
			IRepositoryCommit base, DiffDocument diff, String title) {
		Assert.isLegal(base == null || tip.getRepository().getDirectory()
				.equals(base.getRepository().getDirectory()));
		this.tip = tip;
		this.base = base;
		this.document = diff;
