        for (int i = 0; i < attributes.length; i++) {
            if (!(attributes[i].equals(mq.attributes[i]))) {
				return false;
			}
        }

        return true;
    }
