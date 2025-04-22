		String header = NLS.bind(template, String.format(format, duration / 1000.0), startTime);

		StackSample[] stackTraceSamples = event.getStackTraceSamples();
		if (stackTraceSamples.length == 0 && (event.isStarvedAwake() || event.isStarvedAsleep())) {
			String note =
					(event.isStarvedAwake() || event.isStarvedAsleep()) ?
							Messages.DefaultUiFreezeEventLogger_starved_awake_and_asleep :
					event.isStarvedAwake() ?
							Messages.DefaultUiFreezeEventLogger_starved_awake :
							Messages.DefaultUiFreezeEventLogger_starved_asleep;
			header += note;
		}
