	private MPerspectiveStack getPrimaryPerspectiveStack(MWindow window) {
		List<MWindowElement> winKids = window.getChildren();
		if (winKids.isEmpty()) {
			return null;
		}
		if (instanceCount(winKids, MPerspectiveStack.class) == 1) {
			return firstInstance(winKids, MPerspectiveStack.class);
		}
		if (winKids.size() == 1 && winKids.get(0) instanceof MPartSashContainer) {
			MPartSashContainer topLevelPSC = (MPartSashContainer) winKids.get(0);
			if (instanceCount(topLevelPSC.getChildren(), MPerspectiveStack.class) == 1) {
				return firstInstance(topLevelPSC.getChildren(), MPerspectiveStack.class);
			}
		}
		return null;
	}

	private <T> T firstInstance(Collection<? super T> elements, Class<T> clazz) {
		for (Object o : elements) {
			if (clazz.isInstance(o)) {
				return clazz.cast(o);
			}
		}
		return null;
	}

	private int instanceCount(Collection<?> elements, Class<?> clazz) {
		int count = 0;
		for (Object o : elements) {
			if (clazz.isInstance(o)) {
				count++;
			}
		}
		return count;
	}

