	protected void addPlaceholders(IPlaceholderFolderLayout folder) {
		folder.addPlaceholder("*");
		folder.addPlaceholder(MockViewPart.IDMULT);
		folder.addPlaceholder(MockViewPart.IDMULT + ":secondaryId");
		folder.addPlaceholder(MockViewPart.IDMULT + ":*");
	}
