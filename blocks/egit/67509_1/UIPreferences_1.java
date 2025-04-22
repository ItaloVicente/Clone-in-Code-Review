		for (int i = 0; i < values.length; i++) {
			try {
				int val = Integer.parseInt(values[i].trim());
				if (val <= 0) {
					return null;
				}
				r[i] = val;
			} catch (NumberFormatException e) {
				return null;
			}
