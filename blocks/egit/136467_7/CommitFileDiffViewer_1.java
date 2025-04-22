	@Override
	protected void inputChanged(Object input, Object oldInput) {
		if (input == null) {
			setSelection(StructuredSelection.EMPTY);
		} else if (input instanceof FileDiffInput
				&& oldInput instanceof FileDiffInput) {
			FileDiffInput old = (FileDiffInput) oldInput;
			FileDiffInput newInput = (FileDiffInput) input;
			if (!Objects.equals(old.getRepository(), newInput.getRepository())
					|| !old.getCommit().equals(newInput.getCommit())) {
				setSelection(StructuredSelection.EMPTY);
			}
		}
		super.inputChanged(input, oldInput);
	}

	@Override
	protected void doSetItemCount(int count) {
		if (count == 0) {
			doRemoveAll();
		} else {
			super.doSetItemCount(count);
		}
	}

	@Override
	protected void setSelectionToWidget(List list, boolean reveal) {
		if (list != null && list.isEmpty()) {
			list = null;
		}
		super.setSelectionToWidget(list, reveal);
	}

