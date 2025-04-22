		int hashCode = getName().hashCode();

		if (editPageId != null) {
			hashCode &= editPageId.hashCode();
		}
		return hashCode;
