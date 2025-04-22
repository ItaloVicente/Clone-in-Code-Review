				if (taggedEditor.getTags().contains(IPresentationEngine.SPLIT_HORIZONTAL)) {
					taggedEditor.getTags().remove(IPresentationEngine.SPLIT_HORIZONTAL);
				} else {
					editorPart.getTags().remove(IPresentationEngine.SPLIT_VERTICAL);
					editorPart.getTags().add(IPresentationEngine.SPLIT_HORIZONTAL);
				}
