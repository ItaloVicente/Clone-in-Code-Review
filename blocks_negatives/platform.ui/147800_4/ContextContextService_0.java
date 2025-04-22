		LinkedList<String> activates = (LinkedList<String>) eclipseContext.getLocal(DEFERED_ACTIVATES);
		if (activates != null) {
			eclipseContext.remove(DEFERED_ACTIVATES);
			locals.addAll(activates);
		}
		LinkedList<?> deactivates = (LinkedList<?>) eclipseContext.getLocal(DEFERED_DEACTIVATES);
		if (deactivates != null) {
			eclipseContext.remove(DEFERED_DEACTIVATES);
			for (Object id : deactivates) {
				locals.remove(id);
