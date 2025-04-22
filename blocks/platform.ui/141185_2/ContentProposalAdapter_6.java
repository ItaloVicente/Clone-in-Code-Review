	private String retrieveControlContents() {
		if (asyncProposalComputation) {
			String[] contents = new String[1];
			control.getDisplay().syncExec(() -> {
				contents[0] = getControlContentAdapter().getControlContents(getControl());
			});
			return contents[0];
		}
		return getControlContentAdapter().getControlContents(getControl());
	}

