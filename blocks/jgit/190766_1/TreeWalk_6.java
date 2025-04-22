			AttributesHandler handler = attributesHandlers[index];
			if (handler == null) {
				AbstractTreeIterator tree = trees[index];
				if (!(tree instanceof CanonicalTreeParser)) {
					handler = new AttributesHandler(this
				} else {
					handler = new AttributesHandler(this
							(CanonicalTreeParser) tree);
				}
				attributesHandlers[index] = handler;
