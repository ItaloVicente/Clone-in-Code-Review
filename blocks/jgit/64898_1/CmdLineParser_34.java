		try {
			super.parseArgument(tmp.toArray(new String[tmp.size()]));
		} catch (Die e) {
			if (!seenHelp) {
				throw e;
			}
			printToErrorWriter(CLIText.fatalError(e.getMessage()));
		} finally {
			if (backup != null && !backup.isEmpty()) {
				restoreRequiredOptions(backup);
			}
			seenHelp = false;
		}
	}

	private void printToErrorWriter(String error) {
		if (cmd == null) {
			System.err.println(error);
		} else {
			try {
				cmd.getErrorWriter().println(error);
			} catch (IOException e1) {
				System.err.println(error);
			}
		}
	}

	private List<OptionHandler> unsetRequiredOptions() {
		List<OptionHandler> options = getOptions();
		List<OptionHandler> backup = new ArrayList<>(options);
		for (Iterator<OptionHandler> iterator = options.iterator(); iterator
				.hasNext();) {
			OptionHandler handler = iterator.next();
			if (handler.option instanceof NamedOptionDef
					&& handler.option.required()) {
				iterator.remove();
			}
		}
		return backup;
	}

	private void restoreRequiredOptions(List<OptionHandler> backup) {
		List<OptionHandler> options = getOptions();
		options.clear();
		options.addAll(backup);
	}

	protected boolean containsHelp(final String... args) {
		return TextBuiltin.containsHelp(args);
