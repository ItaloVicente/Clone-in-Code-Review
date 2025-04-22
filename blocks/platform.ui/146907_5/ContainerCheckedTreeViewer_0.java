	@Override
	public boolean setSubtreeChecked(Object element, boolean state) {
		if (super.setSubtreeChecked(element, state)) {
			doCheckStateChanged(element);
			return true;
		}
		return false;
	}

