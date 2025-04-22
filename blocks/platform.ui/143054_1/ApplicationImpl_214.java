	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case ApplicationPackageImpl.APPLICATION___GET_COMMAND__STRING:
			return getCommand((String) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

