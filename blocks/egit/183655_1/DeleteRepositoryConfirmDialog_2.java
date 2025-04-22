		if (agnosticSymLink != null) {
			if (!promptedSymLinkDeletion && shouldDeleteGitDir) {
				promptedSymLinkDeletion = true;
				deleteAgnosticSymLink.setSelection(true);
				shouldDeleteAgnosticSymLink = true;
			}
			if (shouldDeleteWorkingDir) {
				deleteAgnosticSymLink.setSelection(true);
				shouldDeleteAgnosticSymLink = true;
				deleteAgnosticSymLink.setEnabled(false);
				deleteAgnosticSymLinkLabel.setEnabled(false);
			} else {
				deleteAgnosticSymLink.setEnabled(shouldDeleteGitDir);
				deleteAgnosticSymLinkLabel.setEnabled(shouldDeleteGitDir);
			}
		}
