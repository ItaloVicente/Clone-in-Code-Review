	protected static void stage(IFile file) throws Exception {
		ArrayList<IFile> unstaged = new ArrayList<IFile>();
		unstaged.addAll(Arrays.asList(new IFile[] { file }));
		AddToIndexOperation op = new AddToIndexOperation(unstaged);
		op.execute(null);
	}

