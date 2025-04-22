		getTextWidget().addLineBackgroundListener((event) -> {
			IDocument document = getDocument();
			if (document instanceof DiffDocument) {
				try {
					ITypedRegion partition = ((DiffDocument) document)
							.getPartition(event.lineOffset);
					if (partition != null) {
						Color color = backgroundColors.get(partition.getType());
						if (color != null) {
							event.lineBackground = color;
						}
					}
				} catch (BadLocationException e) {
