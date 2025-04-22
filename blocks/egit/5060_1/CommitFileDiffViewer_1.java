
		if (!submoduleSelected) {
			open.setEnabled(!sel.isEmpty());
			openWorkingTreeVersion.setEnabled(!sel.isEmpty());
			compare.setEnabled(sel.size() == 1);
			blame.setEnabled(true);
			if (sel.size() == 1) {
				FileDiff diff = (FileDiff) sel.getFirstElement();
				String path = new Path(getRepository().getWorkTree()
						.getAbsolutePath()).append(diff.getPath()).toOSString();
				compareWorkingTreeVersion.setEnabled(new File(path).exists()
						&& !submoduleSelected);
			} else
				compareWorkingTreeVersion.setEnabled(false);
		} else {
			open.setEnabled(false);
			openWorkingTreeVersion.setEnabled(false);
			compare.setEnabled(false);
			blame.setEnabled(false);
