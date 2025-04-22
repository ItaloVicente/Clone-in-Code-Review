		if (descriptor == null) {
			if (other.descriptor != null)
				return false;
		} else if (!descriptor.equals(other.descriptor))
			return false;
		return true;
