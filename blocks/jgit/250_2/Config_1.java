	public void unsetSection(String section
		State src
		do {
			src = state.get();
			res = unsetSection(src
		} while (!state.compareAndSet(src
	}

	private State unsetSection(final State srcState
			final String subsection) {
		final int max = srcState.entryList.size();
		final ArrayList<Entry> r = new ArrayList<Entry>(max);

		boolean lastWasMatch = false;
		for (Entry e : srcState.entryList) {
			if (e.match(section
				lastWasMatch = true;
				continue;
			}

			if (lastWasMatch && e.section == null && e.subsection == null)
			r.add(e);
		}

		return newState(r);
	}

