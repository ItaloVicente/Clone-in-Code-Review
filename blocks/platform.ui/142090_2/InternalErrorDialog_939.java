		create();
		Button b = getButton(defaultButtonIndex);
		b.setFocus();
		b.getShell().setDefaultButton(b);
		return super.open();
	}

	public void setDetailButton(int index) {
		detailButtonID = index;
	}

	@Override
