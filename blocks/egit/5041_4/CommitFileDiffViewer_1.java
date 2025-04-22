
		if (sel.size() == 1) {
			FileDiff diff = (FileDiff) sel.getFirstElement();
			String path = new Path(getRepository().getWorkTree()
					.getAbsolutePath()).append(diff.getPath()).toOSString();
			compareWorkingTreeVersion.setEnabled(new File(path).exists());
		} else
			compareWorkingTreeVersion.setEnabled(false);
