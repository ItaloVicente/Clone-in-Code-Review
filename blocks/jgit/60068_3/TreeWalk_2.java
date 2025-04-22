				attributesNode = getAttributesNode(dirCacheIterator
						.getEntryAttributesNode(getObjectReader())
						attributesNode);
			}
			if (attributesNode == null && other != null) {
				attributesNode = getAttributesNode(
						other.getEntryAttributesNode(getObjectReader())
						attributesNode);
