		this.value = null;
		if (parent != null) {
			parent.remove();
		}
		else {
			throw new UnsupportedOperationException(
				"Cannot remove an object that is not "
					+ "some other object's property or a collection element");
		}
	}

	public abstract EStructuralFeaturePointer getPropertyPointer();

	public boolean isDynamicPropertyDeclarationSupported() {
		return false;
	}

	@Override
