
	public String getContent() {
		String text = combo.getText();
		for (final IContentProposal p : proposals)
			if (text.equals(p.getLabel()))
				return p.getContent();
		return text;
	}
