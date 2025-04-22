			TreeViewerColumn column = new TreeViewerColumn(viewer, SWT.NONE);
			column.setLabelProvider(createLabelProvider(viewer));
			column = new TreeViewerColumn(viewer, SWT.RIGHT);
			int columnWidth = getColumnWidth(viewer.getTree(),
					UIText.StagingView_Conflict_A_short,
					UIText.StagingView_Conflict_M_short,
					UIText.StagingView_Conflict_DM_short,
					UIText.StagingView_Conflict_MD_short);
			column.getColumn().setWidth(columnWidth);
			column.setLabelProvider(new ColumnLabelProvider() {

				@Override
				public String getText(Object element) {
					if (element instanceof StagingEntry) {
						StagingEntry entry = (StagingEntry) element;
						if (entry.hasConflicts()) {
							StageState conflictType = entry.getConflictType();
							switch (conflictType) {
							case DELETED_BY_THEM:
								return UIText.StagingView_Conflict_MD_short;
							case DELETED_BY_US:
								return UIText.StagingView_Conflict_DM_short;
							case BOTH_MODIFIED:
								return UIText.StagingView_Conflict_M_short;
							case BOTH_ADDED:
								return UIText.StagingView_Conflict_A_short;
							default:
								break;
							}
						}
