			for (int j = 1; iter.setPosition(j); j++) {
				NodePointer childPointer = iter.getNodePointer();
				if (reverse) {
					collection.add(0, childPointer);
				}
				else {
					collection.add(childPointer);
				}
			}
		}
		if (startWith != null) {
			int index = collection.indexOf(startWith);
			if (index == -1) {
				throw new JXPathException(
					"Invalid starting pointer for iterator: " + startWith);
			}
			while (collection.size() > index) {
				if (!reverse) {
					collection.remove(collection.size() - 1);
				}
				else {
					collection.remove(0);
				}
			}
		}
	}
