		if (display == null) {
			if (other.display != null)
				return false;
		} else if (!display.equals(other.display))
			return false;
		return true;
