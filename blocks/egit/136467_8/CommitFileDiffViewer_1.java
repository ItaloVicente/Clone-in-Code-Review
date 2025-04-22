	public void newInput(Object input) {
		if (input == null) {
			setSelection(StructuredSelection.EMPTY);
		} else {
			Object currentInput = getInput();
			if (input instanceof FileDiffInput
					&& currentInput instanceof FileDiffInput) {
				FileDiffInput oldInput = (FileDiffInput) currentInput;
				FileDiffInput newInput = (FileDiffInput) input;
				if (!Objects.equals(oldInput.getRepository(),
						newInput.getRepository())
						|| !oldInput.getCommit().equals(newInput.getCommit())) {
					setSelection(StructuredSelection.EMPTY);
				}
			}
		}
		setInput(input);
	}

	@Override
	protected void setSelectionToWidget(List list, boolean reveal) {
		if (list != null && list.isEmpty()) {
			list = null;
		}
		super.setSelectionToWidget(list, reveal);
	}

