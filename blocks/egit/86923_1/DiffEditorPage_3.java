		viewer.getTextWidget().addCaretListener((event) -> {
			if (outlinePage != null) {
				FileDiffRegion region = getFileDiffRange(event.caretOffset);
				if (region != null && !region.equals(currentFileDiffRange)) {
					currentFileDiffRange = region;
					outlinePage.setSelection(new StructuredSelection(region));
				} else {
					currentFileDiffRange = region;
				}
			}
		});
