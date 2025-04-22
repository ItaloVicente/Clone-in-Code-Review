		if (obj == null || (obj.getClass() != getClass())) {
			return false;
		}
		AbstractDescendantSelector s = (AbstractDescendantSelector)obj;
		return s.simpleSelector.equals(simpleSelector);
	}

	@Override
