		commitLink = new StyledText(displayArea, SWT.READ_ONLY);
		commitLink.addMouseListener(new MouseAdapter() {

			public void mouseUp(MouseEvent e) {
				if (commitLink.getSelectionText().length() != 0)
					return;
				try {
					getShell().dispose();
					CommitEditor.open(new RepositoryCommit(revision
							.getRepository(), revision.getCommit()));
				} catch (PartInitException pie) {
					Activator.logError(pie.getLocalizedMessage(), pie);
				}
