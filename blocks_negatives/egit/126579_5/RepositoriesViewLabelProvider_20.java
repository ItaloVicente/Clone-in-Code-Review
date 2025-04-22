	private StyledString getStyledTextForTag(TagNode node) {
		String tagText = getSimpleText(node);
		if (tagText != null) {
			StyledString styled = new StyledString(tagText);
			if (verboseBranchMode) {
				if (node.getCommitId() != null
						&& node.getCommitId().length() > 0)
					styled.append(' ')
							.append(node.getCommitId().substring(0, 7),
									StyledString.QUALIFIER_STYLER)
							.append(' ')
							.append(node.getCommitShortMessage(),
									StyledString.QUALIFIER_STYLER);
			}
			return styled;
		} else {
			return null;
		}
