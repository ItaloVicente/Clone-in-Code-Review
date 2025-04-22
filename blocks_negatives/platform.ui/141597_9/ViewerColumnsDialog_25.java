			if (key == null) {
				if (other.key != null) {
					return false;
				}
			} else if (!key.equals(other.key)) {
				return false;
			}
			if (keyIndex != other.keyIndex) {
				return false;
			}
			return true;
