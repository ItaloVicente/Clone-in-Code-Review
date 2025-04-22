		MergeTools manager = new MergeTools(db);

		BooleanTriState prompt = BooleanTriState.UNSET;
		BooleanTriState gui = BooleanTriState.UNSET;

		manager.merge(local, remote, merged, base, null, toolName, prompt, gui);
