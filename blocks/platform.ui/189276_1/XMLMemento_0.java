=======
	private static String getAttributeNewValue(Object attributeOldValue) {
		StringBuffer strNewValue = new StringBuffer(FILE_STRING);
		if (attributeOldValue instanceof String && ((String) attributeOldValue).length() != 0) {
			String strOldValue = (String) attributeOldValue;
			boolean exists = Arrays.asList(strOldValue.split(",")).stream().anyMatch(x -> x.trim().equals(FILE_STRING)); //$NON-NLS-1$
			if (!exists) {
				strNewValue.append(", ").append(strOldValue); //$NON-NLS-1$
			} else {
				strNewValue = new StringBuffer(strOldValue);
			}
		}
		return strNewValue.toString();
	}

>>>>>>> CHANGE (d31e90 Bug 577894 - Security Issue -- XXE Attack)
