		boolean msgs = quiet == null || !quiet.booleanValue();
		if (msgs) {
			command.setProgressMonitor(new TextProgressMonitor(errw));
			outw.println(MessageFormat.format(
					CLIText.get().cloningInto
			outw.flush();
		}
