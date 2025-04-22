	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ReflogInput) {
			ReflogInput input = (ReflogInput) inputElement;
			try (Git git = new Git(input.repository)) {
				ReflogCommand command = git.reflog();
				command.setRef(input.ref);
				return command.call().toArray();
