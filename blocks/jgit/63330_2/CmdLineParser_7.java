		List<OptionHandler> backup = null;
		if (seenHelp) {
			backup = unsetRequiredOptions();
		}

		try {
			super.parseArgument(tmp.toArray(new String[tmp.size()]));
		} finally {
			if (backup != null && !backup.isEmpty()) {
				restoreRequiredOptions(backup);
			}
			seenHelp = false;
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
