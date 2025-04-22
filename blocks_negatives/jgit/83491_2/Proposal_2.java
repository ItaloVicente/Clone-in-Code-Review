		synchronized (state) {
			if (state.get() != notIn) {
				return true;
			}
			state.wait(unit.toMillis(wait));
			return state.get() != notIn;
