		DiffTools manager = new DiffTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;
		BooleanTriState trustExitCode = BooleanTriState.TRUE;

		manager.compare(local, remote, merged, toolName, prompt, gui,
				trustExitCode);
