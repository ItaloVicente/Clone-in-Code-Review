	@SuppressWarnings("unchecked")
	private <T> void findElementsRecursive(EObject root, Class<T> clazz, Selector selector,
			List<T> elements, FLAGS flag) {

		boolean classMatch = clazz == null ? isAllowWithFlags(root, flag) : clazz.isInstance(root)
				&& isAllowWithFlags(root, flag);
		if (classMatch && selector.select((MApplicationElement) root)) {
			if (!elements.contains(root)) {
				elements.add((T) root);
			}
		}

		EClass eClass = root.eClass();

		List<Integer> f = flag.getFeatures_by_class().get(eClass);

		if (f != null) {

			for (Integer featureId : f) {
				EStructuralFeature feature = eClass.getEStructuralFeature(featureId);
				if (feature == null)
					continue;

				Object value = root.eGet(feature, false);
				if (value == null)
					continue;

				if (feature.isMany()) {
					for (EObject eObject : (Collection<? extends EObject>) value) {
						findElementsRecursive(eObject, clazz, selector, elements, flag);
					}
				} else {
					findElementsRecursive((EObject) value, clazz, selector, elements, flag);
				}
			}
		}
	}

	private boolean isAllowWithFlags(EObject eObject, FLAGS flag) {

		if (flag.isAllowWithFlags(eObject)) {
			if (flag == FLAGS.IN_ACTIVE_PERSPECTIVE) {
				return !(eObject.eContainer().eClass()
						.equals(ApplicationPackageImpl.Literals.APPLICATION));
			}
			return true;
		}

		return false;
	}

