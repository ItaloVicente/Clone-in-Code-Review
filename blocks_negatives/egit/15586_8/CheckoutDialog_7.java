
		getButton(Window.OK).setEnabled(!branchTree.getSelection().isEmpty());
	}

	private InputDialog getRefNameInputDialog(String prompt,
			final String refPrefix, String initialValue) {
		InputDialog labelDialog = new InputDialog(getShell(),
				UIText.CheckoutDialog_QuestionNewBranchTitle, prompt,
				initialValue, ValidationUtils.getRefNameInputValidator(repo,
						refPrefix, true));
		labelDialog.setBlockOnOpen(true);
		return labelDialog;
	}

	private void reportError(Throwable e, String message, Object... args) {
		String msg = NLS.bind(message, args);
		Activator.handleError(msg, e, true);
	}

	private boolean onlyBranchesAreSelected(TreeSelection selection) {
		Iterator selIterator = selection.iterator();
		while (selIterator.hasNext()) {
			Object sel = selIterator.next();
			if (sel instanceof RefNode) {
				RefNode node = (RefNode) sel;
				String refName = node.getObject().getName();
				if (!refName.startsWith(R_HEADS)
						&& !refName.startsWith(R_REMOTES))
					return false;
			} else
				return false;
		}

		return true;
