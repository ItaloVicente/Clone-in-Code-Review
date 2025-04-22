
					if (nodes instanceof IStreamingNodeList) {
						((IStreamingNodeList) nodes).stream().forEach(child -> {
								applyDefaultStyleDeclaration(child, applyStylesToChildNodes);
						});
					} else {
						int length = nodes.getLength();
						for (int k = 0; k < length; k++) {
							applyDefaultStyleDeclaration(nodes.item(k), applyStylesToChildNodes);
						}
