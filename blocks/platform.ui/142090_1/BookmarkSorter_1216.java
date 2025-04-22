		for (int i = index; i > 0; i--) {
			priorities[i] = priorities[i - 1];
		}
		priorities[0] = priority;
		directions[priority] = DEFAULT_DIRECTIONS[priority];
	}
