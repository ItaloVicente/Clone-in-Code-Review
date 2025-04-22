		NodePointer newParent = parent.createPath(context);
		if (isAttribute()) {
			return newParent.createAttribute(context, getName());
		}
		if (parent instanceof NullPointer && parent.equals(newParent)) {
			throw createBadFactoryException(context.getFactory());
		}
		if (newParent instanceof EStructuralFeatureOwnerPointer) {
			EStructuralFeatureOwnerPointer pop = (EStructuralFeatureOwnerPointer) newParent;
			newParent = pop.getPropertyPointer();
		}
		return newParent.createChild(context, getName(), getIndex());
	}

	@Override
