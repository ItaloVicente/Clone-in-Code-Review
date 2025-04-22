	public void endTask() {
		if (output) {
			if (totalWork == UNKNOWN)
				display(lastWorked, ", done\n");
			else
				display(totalWork, "\n");
