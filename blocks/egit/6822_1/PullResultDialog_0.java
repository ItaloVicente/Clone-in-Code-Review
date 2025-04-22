		int strategy = DIALOG_PERSISTLOCATION;
		if (persistSize)
			strategy |= DIALOG_PERSISTSIZE;
		return strategy;
	}

	@Override
	protected Point getInitialSize() {
		if (!persistSize) {
			int defaultHeight = super.getInitialSize().y;
			persistSize = true;
			try {
				Point size = super.getInitialSize();
				size.y = defaultHeight;
				return size;
			} finally {
				persistSize = false;
			}
		}
		return super.getInitialSize();
