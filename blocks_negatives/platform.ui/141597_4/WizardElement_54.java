		if (wizardDescriptor == null) {
			if (other.wizardDescriptor != null)
				return false;
		} else if (!wizardDescriptor.equals(other.wizardDescriptor))
			return false;
		return true;
