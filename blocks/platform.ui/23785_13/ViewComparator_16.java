		String str2;
		if (e2 instanceof MPartDescriptor) {
			str2 = ((MPartDescriptor) e2).getLocalizedLabel();
		} else {
			str2 = e2.toString();
		}
		if (str1 == null) {
			str1 = EMPTY_STRING;
		}
		if (str2 == null) {
			str2 = EMPTY_STRING;
		}
		String s1 = DialogUtil.removeAccel(str1);
		String s2 = DialogUtil.removeAccel(str2);
		return getComparator().compare(s1, s2);
	}
