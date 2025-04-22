		PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
			Object currentInput = refLogTableTreeViewer.getInput();
			if (currentInput instanceof ReflogInput) {
				ReflogInput oldInput = (ReflogInput) currentInput;
				refLogTableTreeViewer.setInput(new ReflogInput(
						oldInput.getRepository(), oldInput.getRef()));
