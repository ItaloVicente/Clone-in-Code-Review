			AttributesHandler handler = attributesHandlers[attrIndex];
			if (handler == null) {
				if (index < 0) {
					handler = new AttributesHandler(this
						return getTree(CanonicalTreeParser.class);
					});
				} else {
					handler = new AttributesHandler(this
						AbstractTreeIterator tree = trees[index];
						if (tree instanceof CanonicalTreeParser) {
							return (CanonicalTreeParser) tree;
						}
						return null;
					});
				}
				attributesHandlers[attrIndex] = handler;
