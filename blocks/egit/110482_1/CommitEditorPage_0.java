		if (!result.isEmpty()) {
			branchViewer.setInput(result);
			branchSection.setText(MessageFormat.format(
					UIText.CommitEditorPage_SectionBranches,
					Integer.valueOf(result.size())));
		}
