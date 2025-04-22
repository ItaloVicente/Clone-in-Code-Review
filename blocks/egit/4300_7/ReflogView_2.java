
		TreeViewerColumn whoColumn = createColumn(layout,
				UIText.ReflogView_CommitterColumnHeader, 15, SWT.LEFT);
		whoColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				final PersonIdent who = entry.getWho();
				String email = who.getEmailAddress();
				if (email.equals(myEmail))
					return UIText.ReflogView_CommitterMe;
				return who.getName() + " <" + email + ">";  //$NON-NLS-1$//$NON-NLS-2$
			}

			@Override
			public String getToolTipText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				return entry.getNewId().name();
			}

			@Override
			public Image getImage(Object element) {
				return null;
			}

		});

