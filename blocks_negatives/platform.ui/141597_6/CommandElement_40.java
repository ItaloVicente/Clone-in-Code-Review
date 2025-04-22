		if (command == null) {
			if (other.command != null)
				return false;
		} else if (!command.equals(other.command))
			return false;
		return true;
