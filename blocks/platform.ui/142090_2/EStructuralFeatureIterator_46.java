		String[] names = propertyNodePointer.getPropertyNames();
		if (!reverse) {
			if (startPropertyIndex == EStructuralFeaturePointer.UNSPECIFIED_PROPERTY) {
				startPropertyIndex = 0;
			}
			if (startIndex == NodePointer.WHOLE_COLLECTION) {
				startIndex = 0;
			}
			for (int i = startPropertyIndex; i < names.length; i++) {
				if (names[i].equals(name)) {
					propertyNodePointer.setPropertyIndex(i);
					if (i != startPropertyIndex) {
						startIndex = 0;
						includeStart = true;
					}
					empty = false;
					break;
				}
			}
		}
		else {
			if (startPropertyIndex == EStructuralFeaturePointer.UNSPECIFIED_PROPERTY) {
				startPropertyIndex = names.length - 1;
			}
			if (startIndex == NodePointer.WHOLE_COLLECTION) {
				startIndex = -1;
			}
			for (int i = startPropertyIndex; i >= 0; i--) {
				if (names[i].equals(name)) {
					propertyNodePointer.setPropertyIndex(i);
					if (i != startPropertyIndex) {
						startIndex = -1;
						includeStart = true;
					}
					empty = false;
					break;
				}
			}
		}
	}
