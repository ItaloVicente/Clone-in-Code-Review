
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
	public void cloningSubmodule(String path) {
		try {
			outw.println(MessageFormat.format(
					CLIText.get().cloningInto
			outw.flush();
		} catch (IOException e) {
		}
	}

	@Override
	public void checkingOut(AnyObjectId commit
		try {
			outw.println(MessageFormat.format(CLIText.get().checkingOut
					path
			outw.flush();
		} catch (IOException e) {
		}
	}
