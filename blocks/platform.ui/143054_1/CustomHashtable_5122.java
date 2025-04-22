		StringBuilder buffer = new StringBuilder();
		buffer.append('{');
		for (int i = elementData.length; --i >= 0;) {
			HashMapEntry entry = elementData[i];
			while (entry != null) {
				buffer.append(entry.key);
				buffer.append('=');
				buffer.append(entry.value);
				buffer.append(", "); //$NON-NLS-1$
				entry = entry.next;
			}
		}
		if (elementCount > 0) {
