		TreeViewerColumn commitMessageColumn = createColumn(layout,
				UIText.ReflogView_CommitMessageColumnHeader, 30, SWT.LEFT);
		commitMessageColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				RevCommit c = getCommit(entry);
				return c == null ? "" : c.getShortMessage(); //$NON-NLS-1$
			}

			private RevCommit getCommit(final ReflogEntry entry) {
				RevWalk walk = new RevWalk(getRepository());
				walk.setRetainBody(true);
				RevCommit c = null;
				try {
					c = walk.parseCommit(entry.getNewId());
				} catch (IOException ignored) {
				} finally {
					walk.release();
				}
				return c;
			}

			@Override
			public Image getImage(Object element) {
				return branchImage;
			}

		});

