			Object selection = getSelection(ctx);
			if (selection instanceof ISelection) {
				Repository repo = getRepository((ISelection) selection, getActiveEditorInput(ctx));
				if (repo != null) {
					boolean enabled = isEnabledForState(repo,
							repo.getRepositoryState());
					setBaseEnabled(enabled);
				} else
					setBaseEnabled(false);
				return;
