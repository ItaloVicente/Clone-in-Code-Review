	@Override
	protected void handleDispose(DisposeEvent event) {
		cancelJob();
		realInput = null;
		super.handleDispose(event);
	}

	public void newInput(Object input) {
		cancelJob();
		if (input == null) {
			setSelection(StructuredSelection.EMPTY);
		} else {
			if (realInput != null) {
				if (input instanceof FileDiffInput) {
					FileDiffInput newInput = (FileDiffInput) input;
					if (!Objects.equals(realInput.getRepository(),
							newInput.getRepository())
							|| !realInput.getCommit()
									.equals(newInput.getCommit())) {
						setSelection(StructuredSelection.EMPTY);
						setInput(null);
					}
				}
			}
		}
		if (input instanceof FileDiffInput) {
			realInput = (FileDiffInput) input;
			startJob((FileDiffInput) input);
		} else {
			realInput = null;
			setInput(input);
		}
	}

	@Override
	protected void setSelectionToWidget(List list, boolean reveal) {
		if (list != null && list.isEmpty()) {
			list = null;
		}
		super.setSelectionToWidget(list, reveal);
	}

