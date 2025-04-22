		Control control = refLogTreeViewer.getControl();
		if (control != null && !control.isDisposed()) {
			control.getDisplay().asyncExec(() -> {
				if (control.isDisposed()) {
					return;
				}
				Object currentInput = refLogTreeViewer.getInput();
				if (currentInput instanceof ReflogInput) {
					ReflogInput oldInput = (ReflogInput) currentInput;
					Repository repo = oldInput.getRepository();
					if (repo.getDirectory()
							.equals(event.getRepository().getDirectory())) {
						updateView(new ReflogInput(oldInput.getRepository(),
								oldInput.getRef()));
