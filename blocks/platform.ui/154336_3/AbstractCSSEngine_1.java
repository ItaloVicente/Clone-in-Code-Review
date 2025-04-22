						if (nodes instanceof IStreamingNodeList) {
							((IStreamingNodeList) nodes).stream().forEach(child -> {
								applyStyles(child, applyStylesToChildNodes);
							});
						} else {
							int length = nodes.getLength();
							for (int k = 0; k < length; k++) {
								applyStyles(nodes.item(k), applyStylesToChildNodes);
							}
