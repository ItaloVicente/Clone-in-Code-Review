	private boolean objectExist(RevCommit commit, ObjectId id) {
		return commit != null && id != null && !id.equals(zeroId());
	}

	private String getFileRevisionLabel(ITypedElement element) {
		if (element instanceof FileRevisionTypedElement) {
			FileRevisionTypedElement castElement = (FileRevisionTypedElement)element;
			return NLS.bind(UIText.GitCompareFileRevisionEditorInput_RevisionLabel,
					new Object[]{element.getName(),
					CompareUtils.truncatedRevision(castElement.getContentIdentifier()),
					castElement.getAuthor()});

