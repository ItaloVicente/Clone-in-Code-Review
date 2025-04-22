	private void setFeedback(DropTargetEvent event, int location) {
		if (feedbackEnabled) {
			switch (location) {
			case LOCATION_BEFORE:
				event.feedback = DND.FEEDBACK_INSERT_BEFORE;
				break;
			case LOCATION_AFTER:
				event.feedback = DND.FEEDBACK_INSERT_AFTER;
				break;
			case LOCATION_ON:
			default:
				event.feedback = DND.FEEDBACK_SELECT;
				break;
			}
		}

