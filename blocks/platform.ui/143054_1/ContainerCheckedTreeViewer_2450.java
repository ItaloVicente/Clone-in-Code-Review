	@Override
	public boolean setChecked(Object element, boolean state) {
		if (super.setChecked(element, state)) {
			doCheckStateChanged(element);
			return true;
		}
		return false;
	}

	@Override
