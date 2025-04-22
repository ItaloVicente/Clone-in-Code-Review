		if (part == null) {
			if (other.part != null)
				return false;
		} else if (!part.equals(other.part))
			return false;
		if (getSelection() == null) {
			if (other.getSelection() != null)
				return false;
		} else if (!getSelection().equals(other.getSelection()))
			return false;
		if (getInput() == null || other.getInput() == null) {
