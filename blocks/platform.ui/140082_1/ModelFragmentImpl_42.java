	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case FragmentPackageImpl.MODEL_FRAGMENT___MERGE__MAPPLICATION:
				return merge((MApplication)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

