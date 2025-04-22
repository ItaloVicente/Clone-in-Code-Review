	private void updateProposals() {
		String[] substring = new String[1];
		directoryCombo.getDisplay().syncExec(() -> {
			substring[0] = directoryCombo.getText().substring(0, directoryCombo.getCaretPosition());
		});
		updateProposals(substring[0]);
	}
