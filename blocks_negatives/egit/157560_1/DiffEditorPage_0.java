	/**
	 * An editor input that gives access to the document created by the diff
	 * formatter.
	 */
	private static class DiffEditorInput extends CommitEditorInput {

		private IDocument document;

		public DiffEditorInput(RepositoryCommit commit, IDocument diff) {
			super(commit);
			document = diff;
		}

		public IDocument getDocument() {
			return document;
		}

		@Override
		public String getName() {
			return UIText.DiffEditorPage_Title;
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj) && (obj instanceof DiffEditorInput)
					&& Objects.equals(document,
							((DiffEditorInput) obj).document);
		}

		@Override
		public int hashCode() {
			return super.hashCode() ^ Objects.hashCode(document);
		}
	}

