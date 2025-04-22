		if (editorReference == null) {
			if (other.editorReference != null)
				return false;
		} else if (!editorReference.equals(other.editorReference))
			return false;
		return true;
