		if (d.getBlobs().length == 2) {
			if (d.getChange().equals(ChangeType.ADD)) {
				final String previousPath = findPreviousPath(p, c);
				if (previousPath != null)
					base = CompareUtils.getFileRevisionTypedElement(
							previousPath, c.getParent(0), getRepository(),
							d.getBlobs()[0]);
				else
					base = new GitCompareFileRevisionEditorInput.EmptyTypedElement(
							""); //$NON-NLS-1$
			} else
				base = CompareUtils.getFileRevisionTypedElement(p,
						c.getParent(0), getRepository(), d.getBlobs()[0]);
		} else
