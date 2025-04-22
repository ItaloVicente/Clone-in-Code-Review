
			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					base, next, ancestor, null);
			in.getCompareConfiguration()
					.setRightLabel(dlg.getCommitId().name());
			CompareUI.openCompareEditor(in);
