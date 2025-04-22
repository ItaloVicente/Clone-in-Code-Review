		if (d.blobs.length == 2) {
			base = CompareUtils.getFileRevisionTypedElement(p, c.getParent(0), db, d.blobs[0]);
			next = CompareUtils.getFileRevisionTypedElement(p, c, db, d.blobs[1]);
		} else {
			base = new GitCompareFileRevisionEditorInput.EmptyTypedElement(""); //$NON-NLS-1$
			next = CompareUtils.getFileRevisionTypedElement(p, c, db, d.blobs[0]);
		}
