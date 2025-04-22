		if (newValue instanceof String) {
			store.setValue(propertyId, (String) newValue);
		} else if (newValue instanceof Integer) {
			store.setValue(propertyId, ((Integer) newValue).intValue());
		} else if (newValue instanceof Boolean) {
			store.setValue(propertyId, ((Boolean) newValue).booleanValue());
		} else if (newValue instanceof Double) {
			store.setValue(propertyId, ((Double) newValue).doubleValue());
		} else if (newValue instanceof Float) {
			store.setValue(propertyId, ((Float) newValue).floatValue());
		} else if (newValue instanceof Integer) {
			store.setValue(propertyId, ((Integer) newValue).intValue());
		} else if (newValue instanceof Long) {
			store.setValue(propertyId, ((Long) newValue).longValue());
		}
	}
