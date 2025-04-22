	private static void openInCompareExternal(RevCommit commit1,
			RevCommit commit2,
			String commit1Path, String commit2Path, Repository repository,
			IWorkbenchPage workBenchPage) {
		String diffCmd = GitPreferenceRoot.getExternalDiffToolCommand();

		MessageBox mbox = new MessageBox(Display.getCurrent().getActiveShell(),
                SWT.ICON_INFORMATION | SWT.OK);
		mbox.setText("getExternalDiffToolCommand"); //$NON-NLS-1$
		mbox.setMessage(diffCmd);
		mbox.open();
	}

