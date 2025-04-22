		MutableActivityManager clone = new MutableActivityManager(advisor, activityRegistry);
		clone.setEnabledActivityIds(getEnabledActivityIds());
		return clone;
	}

	private Job getUpdateJob() {
		if (deferredIdentifierJob == null) {
			deferredIdentifierJob = new Job("Identifier Update Job") { //$NON-NLS-1$

				protected IStatus run(IProgressMonitor monitor) {
					while (!deferredIdentifiers.isEmpty()) {
						Identifier identifier = deferredIdentifiers.remove(0);
						Set<String> activityIds = new HashSet<String>();
						for (Iterator<String> iterator = definedActivityIds.iterator(); iterator
								.hasNext();) {
							String activityId = iterator.next();
							Activity activity = (Activity) getActivity(activityId);

							if (activity.isMatch(identifier.getId())) {
								activityIds.add(activityId);
							}
						}

						boolean activityIdsChanged = identifier.setActivityIds(activityIds);
						if (activityIdsChanged) {
							IdentifierEvent identifierEvent = new IdentifierEvent(identifier,
									activityIdsChanged, false);
							final Map<String, IdentifierEvent> identifierEventsByIdentifierId = new HashMap<String, IdentifierEvent>(
									1);
							identifierEventsByIdentifierId.put(identifier.getId(), identifierEvent);
							UIJob notifyJob = new UIJob("Identifier Update Job") { //$NON-NLS-1$

								public IStatus runInUIThread(IProgressMonitor monitor) {
