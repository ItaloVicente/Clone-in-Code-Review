		if (position == 0) {
			if (name != null) {
				if (!targetReady) {
					prepareForIndividualProperty(name);
				}
				if (empty) {
					return null;
				}
			}
			else {
				if (!setPosition(1)) {
					return null;
				}
				reset();
			}
		}
		try {
			return propertyNodePointer.getValuePointer();
		}
		catch (Throwable ex) {
			NullEStructuralFeaturePointer npp =
				new NullEStructuralFeaturePointer(
						propertyNodePointer.getImmediateParentPointer());
			npp.setPropertyName(propertyNodePointer.getPropertyName());
			npp.setIndex(propertyNodePointer.getIndex());
			return npp.getValuePointer();
		}
	}
