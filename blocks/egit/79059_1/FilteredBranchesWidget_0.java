		ColumnLabelProvider dateLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				if (element instanceof Ref) {
					String name = ((Ref) element).getName().substring(Constants.R_HEADS.length());
					RevCommit revCommit = gfRepo.findHead(name);
					if (revCommit == null) {
						return ""; //$NON-NLS-1$
					}
					return getDateFormatter().formatDate(revCommit.getAuthorIdent());
				}
				return super.getText(element);
			}};
		TreeColumn dateColumn = createColumn(UIText.FilteredBranchesWidget_dateOfLastCommit, branchesViewer, dateLabelProvider);
		setSortedColumn(dateColumn, dateLabelProvider);

