		StringBuilder buffer = new StringBuilder();
		NodePointer parent = getImmediateParentPointer();
		if (parent != null) {
			buffer.append(parent.asPath());
			if (index != WHOLE_COLLECTION) {
				if (parent.getIndex() != WHOLE_COLLECTION) {
					buffer.append("/.");
				}
				buffer.append("[").append(index + 1).append(']');
			}
		}
		else {
			if (index != WHOLE_COLLECTION) {
				buffer.append("/.[").append(index + 1).append(']');
			}
			else {
				buffer.append("/");
			}
		}
		return buffer.toString();
	}
