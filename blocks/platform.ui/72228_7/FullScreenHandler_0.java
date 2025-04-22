	boolean checkDuplicatedEvent(ExecutionEvent event) {
		if (event != null && event.getTrigger() != null && event.getTrigger() instanceof Event) {
			int time = ((Event) event.getTrigger()).time;
			if (time == timeLastEvent) {
				return true;
			}
			timeLastEvent = time;
		}
		return false;
	}

