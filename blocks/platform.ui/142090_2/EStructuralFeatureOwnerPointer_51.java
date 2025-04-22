		this.value = value;
		if (parent != null) {
			if (parent.isContainer()) {
				parent.setValue(value);
			}
			else {
				if (index == WHOLE_COLLECTION) {
					throw new UnsupportedOperationException(
						"Cannot setValue of an object that is not "
							+ "some other object's property");
				}
				throw new JXPathInvalidAccessException(
					"The specified collection element does not exist: " + this);
			}
		}
		else {
			throw new UnsupportedOperationException(
				"Cannot replace the root object");
		}
	}

	@Override
