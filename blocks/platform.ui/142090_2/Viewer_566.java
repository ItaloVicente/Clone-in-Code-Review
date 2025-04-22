			if (keys.length == 1) {
				keys = null;
				values = null;
			} else {
				String[] newKeys = new String[keys.length - 1];
				Object[] newValues = new Object[values.length - 1];
				System.arraycopy(keys, 0, newKeys, 0, index);
				System.arraycopy(keys, index + 1, newKeys, index,
						newKeys.length - index);
				System.arraycopy(values, 0, newValues, 0, index);
				System.arraycopy(values, index + 1, newValues, index,
						newValues.length - index);
				keys = newKeys;
				values = newValues;
			}
			return;
		}
