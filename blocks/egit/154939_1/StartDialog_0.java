	@Override
	protected Control createDialogArea(Composite parent) {
		Control result = super.createDialogArea(parent);
		BranchNameNormalizer normalizer = new BranchNameNormalizer(getText());
		normalizer.setVisible(false);
		return result;
	}

