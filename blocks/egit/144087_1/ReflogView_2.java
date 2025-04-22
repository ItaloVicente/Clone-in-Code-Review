			updateView(new ReflogInput(repository, ref));
		}
	}

	private void updateView(ReflogInput input) {
		if (input != null) {
			if (!partListener.isVisible()) {
				pendingInput = input;
				return;
			}
			pendingInput = null;
			Object currentInput = refLogTreeViewer.getInput();
			boolean repoChanged = true;
			boolean needRefUpdate = true;
			if (currentInput instanceof ReflogInput) {
				ReflogInput oldInput = (ReflogInput) currentInput;
				repoChanged = oldInput.getRepository() != input.getRepository();
				needRefUpdate = repoChanged
						|| !oldInput.getRef().equals(input.getRef());
			}
			if (!hasRef(input)) {
				input = new ReflogInput(input.getRepository(), Constants.HEAD);
				needRefUpdate = true;
			}
			refLogTreeViewer.setInput(input);
			if (needRefUpdate) {
				updateRefLink(input.getRef());
			}
			if (repoChanged) {
				form.setText(getRepositoryName(input.getRepository()));
			}
		} else {
			refLogTreeViewer.setInput(null);
			form.setText(UIText.StagingView_NoSelectionTitle);
			IToolBarManager toolbar = form.getToolBarManager();
			toolbar.removeAll();
			toolbar.update(true);
		}
	}

	private static boolean hasRef(ReflogInput input) {
		try {
			return input.getRepository().findRef(input.getRef()) != null;
		} catch (IOException e) {
			return false;
