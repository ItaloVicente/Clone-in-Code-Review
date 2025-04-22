	private HiddenAttributes attrs = null;

	public JGitHiddenAttributeViewImpl(final JGitPathImpl path) {
		super(path);
	}

	@Override
	public HiddenAttributes readAttributes() throws IOException {
		if (attrs == null) {
			attrs = buildAttrs(path.getFileSystem()
		}
		return attrs;
	}

	@Override
	public Class<? extends BasicFileAttributeView>[] viewTypes() {
		return new Class[] { HiddenAttributeView.class
				JGitVersionAttributeViewImpl.class };
	}

	private HiddenAttributes buildAttrs(final JGitFileSystem fileSystem
			throws IOException {
		return new HiddenAttributesImpl(new JGitBasicAttributeView(this.path).readAttributes()
				HiddenBranchRefFilter.isHidden(refTree));
	}
