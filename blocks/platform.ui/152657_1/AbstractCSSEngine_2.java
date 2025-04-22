		if (applyStylesToChildNodes) {
			NodeList nodes = elt instanceof ChildVisibilityAwareElement
					? ((ChildVisibilityAwareElement) elt).getVisibleChildNodes()
							: elt.getChildNodes();
					if (nodes != null) {
						for (int k = 0; k < nodes.getLength(); k++) {
							applyStyles(nodes.item(k), applyStylesToChildNodes);
