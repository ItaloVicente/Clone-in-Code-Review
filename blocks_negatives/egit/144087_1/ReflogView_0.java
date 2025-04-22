		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			Object currentInput = refLogTreeViewer.getInput();
			if (currentInput instanceof ReflogInput) {
				ReflogInput oldInput = (ReflogInput) currentInput;
				Repository repo = oldInput.getRepository();
				if (repo.getDirectory()
						.equals(event.getRepository().getDirectory())) {
					try {
						if (repo.findRef(oldInput.getRef()) != null) {
							refLogTreeViewer.setInput(
									new ReflogInput(oldInput.getRepository(),
											oldInput.getRef()));
							return;
						}
					} catch (IOException e) {
