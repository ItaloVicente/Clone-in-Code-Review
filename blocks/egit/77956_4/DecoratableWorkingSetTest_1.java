	static {
		WORKING_SET = WORKING_SET_MANAGER.getWorkingSet(WORKING_SET_NAME);
		if (WORKING_SET == null) {
			WORKING_SET = WORKING_SET_MANAGER.createWorkingSet(WORKING_SET_NAME,
					new IAdaptable[0]);
		}
	}
