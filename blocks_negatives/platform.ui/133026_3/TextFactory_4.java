
	@Override
	protected void applyProperties(Text text) {
		super.applyProperties(text);

		if (this.text != null) {
			text.setText(this.text);
		}
		if (this.limit > -1) {
			text.setTextLimit(this.limit);
		}
		if (this.message != null) {
			text.setMessage(this.message);
		}
		this.selectionListeners.forEach(l -> text.addSelectionListener(l));
		this.modifyListeners.forEach(l -> text.addModifyListener(l));
		this.verifyListeners.forEach(l -> text.addVerifyListener(l));
	}
