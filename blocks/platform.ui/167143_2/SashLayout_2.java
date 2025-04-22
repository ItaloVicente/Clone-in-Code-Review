	private boolean isForHostShell(Event e) {
		if (e.widget instanceof Control && ((Control) e.widget).getShell() == host.getShell()) {
			return true;
		}
		return false;
	}

