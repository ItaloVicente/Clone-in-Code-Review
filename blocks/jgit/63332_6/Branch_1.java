	@Option(name = "--delete"
			"-d" }
	public void delete(List<String> names) {
		if (names.isEmpty()) {
			throw die(CLIText.get().branchNameRequired);
		}
		delete = names;
	}
