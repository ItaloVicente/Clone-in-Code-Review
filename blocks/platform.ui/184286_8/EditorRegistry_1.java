
		final Program[] programs[] = new Program[1][];
		Display.getDefault().syncExec(() -> {
			programs[0] = Program.getPrograms();
		});
		for (Program program : programs[0]) {
