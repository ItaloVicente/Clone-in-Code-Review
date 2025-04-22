        if (map == null) {
            return null;
        }
        RefCount refCount = map.get(descriptor);
        if (refCount == null)
        	return null;
