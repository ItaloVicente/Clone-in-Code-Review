
	@Override
	public void initializedSubmodules(Collection<String> submodules) {
		try {
			for (String submodule : submodules) {
				outw.println(MessageFormat
						.format(CLIText.get().submoduleRegistered
			}
			outw.flush();
		} catch (IOException e) {
		}
	}

	@Override
	public void cloningSubmodule(String name) {
		try {
			outw.println(MessageFormat.format(
					CLIText.get().cloningInto
			outw.flush();
		} catch (IOException e) {
		}
	}
