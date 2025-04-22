		if (index == WHOLE_COLLECTION) {
			parent.setValue(value);
		}
		else {
			ValueUtils.setValue(collection, index, value);
		}
	}
