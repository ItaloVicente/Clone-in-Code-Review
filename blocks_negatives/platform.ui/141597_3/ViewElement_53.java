		if (viewDescriptor == null) {
			if (other.viewDescriptor != null)
				return false;
		} else if (!viewDescriptor.equals(other.viewDescriptor))
			return false;
		return true;
