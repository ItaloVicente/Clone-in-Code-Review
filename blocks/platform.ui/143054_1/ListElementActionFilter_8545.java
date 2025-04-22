		called = true;
		ListElement le = (ListElement) target;
		if (name.equals(ATTR_NAME)) {
			return value.equals(le.getName());
		} else if (name.equals(ATTR_FLAG)) {
			boolean flag = le.getFlag();
			if (flag) {
