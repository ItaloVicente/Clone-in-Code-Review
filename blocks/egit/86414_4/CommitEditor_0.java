	private static class CommitEditorNestedSite extends MultiPageEditorSite {

		public CommitEditorNestedSite(CommitEditor topLevelEditor,
				IEditorPart nestedEditor) {
			super(topLevelEditor, nestedEditor);
		}

		@Override
		public IEditorActionBarContributor getActionBarContributor() {
			IEditorActionBarContributor globalContributor = getMultiPageEditor()
					.getEditorSite().getActionBarContributor();
			if (globalContributor instanceof CommitEditorActionBarContributor) {
				return ((CommitEditorActionBarContributor) globalContributor)
						.getTextEditorActionContributor();
			}
			return super.getActionBarContributor();
		}

	}

	@Override
	protected IEditorSite createSite(IEditorPart editor) {
		return new CommitEditorNestedSite(this, editor);
	}

