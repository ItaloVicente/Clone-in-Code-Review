
	@Override
	public void fetchingSubmodule(String name) {
		try {
			outw.println(MessageFormat.format(CLIText.get().fetchingSubmodule
					name));
			outw.flush();
		} catch (IOException e) {
		}
	}
