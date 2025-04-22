	private static void fireSynchronizeAction(
			GitModelSynchronizeParticipant participant, IWorkbenchWindow window) {
		IWorkbenchPart activePart = null;
		if (window != null)
			activePart = window.getActivePage().getActivePart();

		participant.run(activePart);
	}

