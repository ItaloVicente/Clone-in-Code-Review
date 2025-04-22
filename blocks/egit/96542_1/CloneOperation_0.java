		CloneCommand.Callback callback = new CloneCommand.Callback() {

			@Override
			public void initializedSubmodules(Collection<String> submodules) {
			}

			@Override
			public void cloningSubmodule(String path) {
				progress.setTaskName(NLS.bind(
						CoreText.CloneOperation_submodule_title, uri, path));
			}

			@Override
			public void checkingOut(AnyObjectId commit, String path) {
			}
		};
