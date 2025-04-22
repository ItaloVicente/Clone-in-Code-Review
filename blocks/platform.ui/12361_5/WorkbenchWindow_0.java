	private void disableControl(Control ctrl, List<Control> toEnable) {
		if (ctrl != null && !ctrl.isDisposed() && ctrl.isEnabled()) {
			ctrl.setEnabled(false);
			toEnable.add(ctrl);
		}
	}

