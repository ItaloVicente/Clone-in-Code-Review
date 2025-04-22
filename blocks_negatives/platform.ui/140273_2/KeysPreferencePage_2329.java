	 * Updates the entire preference page -- except the view tab -- based on
	 * current selection sate. This preference page is written so that
	 * everything can be made consistent simply by inspecting the state of its
	 * widgets. A change is triggered by the user, and an event is fired. The
	 * event triggers an update. It is possible for extra work to be done by
	 * this page before calling update.
