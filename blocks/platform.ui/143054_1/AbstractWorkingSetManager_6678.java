		if (this == object) {
			return true;
		}
		if (!getClass().getName().equals(object.getClass().getName())) {
			return false;
		}
		AbstractWorkingSetManager other = (AbstractWorkingSetManager) object;
		return other.workingSets.equals(workingSets);
	}
