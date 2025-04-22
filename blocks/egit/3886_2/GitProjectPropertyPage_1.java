				headLink.setText(UIText.GitProjectPropertyPage_ValueUnbornBranch);
		} else {
			headLink.setText(objectId.name());
			headLink.setUnderlined(true);
			headLink.setFont(JFaceResources.getDialogFont());
			headLink.setForeground(JFaceColors.getHyperlinkText(headLink
					.getDisplay()));
			headLink.addHyperlinkListener(new HyperlinkAdapter() {
				@Override
				public void linkActivated(HyperlinkEvent e) {
					RepositoryCommit commit = getCommit(repository, objectId);
					if(commit != null) {
						CommitEditor.openQuiet(commit);
					}
				}
			});
		}
