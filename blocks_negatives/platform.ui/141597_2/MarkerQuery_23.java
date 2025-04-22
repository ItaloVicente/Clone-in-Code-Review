        if (!(type == null ? mq.type == null : type.equals(mq.type))) {
			return false;
		}

        if (matchTypeChildren != mq.matchTypeChildren) {
			return false;
        }

        if (attributes.length != mq.attributes.length) {
			return false;
		}

        for (int i = 0; i < attributes.length; i++) {
            if (!(attributes[i].equals(mq.attributes[i]))) {
				return false;
			}
        }

        return true;
