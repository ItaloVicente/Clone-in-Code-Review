			if (field == null) {
				if (other.field != null) {
					return false;
				}
			} else if (!field.equals(other.field)) {
				return false;
			}
			return true;
