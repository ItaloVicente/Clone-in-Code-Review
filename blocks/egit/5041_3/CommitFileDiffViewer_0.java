		compareWorkingTreeVersion = new Action(
				UIText.CommitFileDiffViewer_CompareWorkingDirectoryMenuLabel) {
			@Override
			public void run() {
				showWorkingDirectoryFileDiff(getDiff());
			}

			public boolean isEnabled() {
				FileDiff diff = getDiff();
				if (diff == null)
					return false;
				String path = new Path(getRepository().getWorkTree()
						.getAbsolutePath()).append(diff.getPath()).toOSString();
				return new File(path).exists();
			}

			private FileDiff getDiff() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return null;
				return (FileDiff) ((IStructuredSelection) s).getFirstElement();
			}
		};

