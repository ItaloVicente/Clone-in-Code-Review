        if (values.length != mqr.values.length) {
			return false;
		}

        for (int i = 0; i < values.length; i++) {
            if (!(values[i].equals(mqr.values[i]))) {
				return false;
			}
        }

        return true;
