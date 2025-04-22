		if (diffs.isEmpty()) {
			if (UIUtils.isUsable(diffViewer)) {
				IDocument document = new Document();
				diffViewer.setDocument(document);
				diffViewer.setFormatter(null);
			}
			return;
		}

