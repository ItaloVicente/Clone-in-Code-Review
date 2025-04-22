
					if (nodes instanceof IStreamingNodeList) {
						((IStreamingNodeList) nodes).stream().forEach(child -> {
							try {
								applyInlineStyle(child, applyStylesToChildNodes);
							} catch (IOException e) {
								handleExceptions(e);
							}
						});
					} else {
						int length = nodes.getLength();
						for (int k = 0; k < length; k++) {
							applyInlineStyle(nodes.item(k), applyStylesToChildNodes);
						}
