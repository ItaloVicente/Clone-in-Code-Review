				if (l instanceof IStreamingNodeList) {
					return ((IStreamingNodeList) l).stream().anyMatch(node -> node == elt);
				} else {
					int length = l.getLength();
					for (int i = 0; i < length; i++) {
						if (l.item(i) == elt) {
							return true;
						}
