	private void addMnemonic(BuildSetAction action, int index) {
		StringBuilder label = new StringBuilder();
		if (index < 9) {
			label.append('&');
			label.append(index);
			label.append(' ');
		}
		label.append(action.getWorkingSet().getLabel());
		action.setText(label.toString());
	}
