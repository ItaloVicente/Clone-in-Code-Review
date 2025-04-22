			if (showTags.get()) {
				nodeList.add(new TagsNode(node, repo));
			}
			if (showRefs.get()) {
				nodeList.add(new AdditionalRefsNode(node, repo));
			}
