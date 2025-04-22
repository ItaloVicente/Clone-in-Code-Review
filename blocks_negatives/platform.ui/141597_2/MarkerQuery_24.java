        hashCode = 19;

        if (type != null) {
			hashCode = hashCode * 37 + type.hashCode();
		}

		hashCode = hashCode * 37 + (matchTypeChildren ? 1 : 2);

        for (String attribute : attributes) {
            hashCode = hashCode * 37 + attribute.hashCode();
        }
