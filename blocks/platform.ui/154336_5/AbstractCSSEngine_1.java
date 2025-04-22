	private void processNodeList(NodeList nodes, BiConsumer<Node, Boolean> consumer, boolean applyStylesToChildNodes) {
		if (nodes instanceof IStreamingNodeList) {
			((IStreamingNodeList) nodes).stream().forEach(child -> {
				consumer.accept(child, applyStylesToChildNodes);
			});
		} else {
			int length = nodes.getLength();
			for (int k = 0; k < length; k++) {
				consumer.accept(nodes.item(k), applyStylesToChildNodes);
			}
		}
	}

