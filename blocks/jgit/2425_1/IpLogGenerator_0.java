	private String mapCQState(String state) {
		if (state.equals("approved"))
			return "active";
		return state;
	}

