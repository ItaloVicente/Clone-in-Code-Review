			return refName;
		case WORKINGDIR:
			StyledString dirString = new StyledString(
					UIText.RepositoriesView_WorkingDir_treenode);
			dirString.append(" - ", StyledString.QUALIFIER_STYLER); //$NON-NLS-1$
			dirString.append(node.getRepository().getWorkTree()
					.getAbsolutePath(), StyledString.QUALIFIER_STYLER);
			return dirString;

		case REF:
			StyledString styled = null;
			String nodeText = getSimpleText(node);
			if (nodeText != null) {
				styled = new StyledString(nodeText);
				if (verboseBranchMode) {
					RevCommit latest = getLatestCommit(node);
					if (latest != null)
						styled.append(' ')
								.append(abbreviate(latest),
										StyledString.QUALIFIER_STYLER)
								.append(' ')
								.append(latest.getShortMessage(),
										StyledString.QUALIFIER_STYLER);
				}
