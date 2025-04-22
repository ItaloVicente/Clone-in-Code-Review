
	@Override
	protected void applyProperties(Text control) {
		super.applyProperties(control);
		if (this.text != null) {
			control.setText(this.text);
		}
		if (this.limit > -1) {
			control.setTextLimit(this.limit);
		}
		if (this.message != null) {
			control.setMessage(this.message);
		}
		this.selectionListeners.forEach(l -> control.addSelectionListener(l));
		this.modifyListeners.forEach(l -> control.addModifyListener(l));
		this.verifyListeners.forEach(l -> control.addVerifyListener(l));
	}
