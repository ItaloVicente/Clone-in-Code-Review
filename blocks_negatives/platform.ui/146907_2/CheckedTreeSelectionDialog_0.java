			if (fContainerMode) {
				fViewer.setCheckedElements(viewerElements);
			} else {
				for (Object viewerElement : viewerElements) {
					fViewer.setSubtreeChecked(viewerElement, true);
				}
