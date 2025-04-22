				if (nodes != null) {
					for (int k = 0; k < nodes.getLength(); k++) {
						applyStyles(nodes.item(k), applyStylesToChildNodes);
					}
					onStylesAppliedToChildNodes(elt, nodes);
				}
