		branchesViewer.getTree().setHeaderVisible(true);

		TreeColumn nameColumn = createColumn(UIText.BranchSelectionTree_NameColumn, branchesViewer, createNameLabelProvider());
		TreeColumn idColumn = createColumn(UIText.BranchSelectionTree_IdColumn, branchesViewer, new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
				if (element instanceof Ref) {
					ObjectId objectId = ((Ref) element).getObjectId();
					if (objectId == null) {
						return ""; //$NON-NLS-1$
					}
					return objectId.abbreviate(7).name();
				}
				return super.getText(element);
			}});
		TreeColumn msgColumn = createColumn(UIText.BranchSelectionTree_MessageColumn, branchesViewer, new ColumnLabelProvider() {
			
			@Override
			public String getText(Object element) {
				if (element instanceof Ref) {
					String name = ((Ref) element).getName().substring(Constants.R_HEADS.length());
					RevCommit revCommit = gfRepo.findHead(name);
					if (revCommit == null) {
						return ""; //$NON-NLS-1$
					}
					return revCommit.getShortMessage();
				}
				return super.getText(element);
			}});
