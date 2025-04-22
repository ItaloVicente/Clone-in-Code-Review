			deferredIdentifierJob = Job.create("Activity Identifier Update", new IJobFunction() { //$NON-NLS-1$
                @Override
				public IStatus run(IProgressMonitor monitor) {
					final Map identifierEventsByIdentifierId = new HashMap();

                    while (!deferredIdentifiers.isEmpty()) {
                        Identifier identifier = (Identifier) deferredIdentifiers.remove(0);
                        Set activityIds = new HashSet();
                        for (Iterator iterator = definedActivityIds.iterator(); iterator
                                .hasNext();) {
                            String activityId = (String) iterator.next();
                            Activity activity = (Activity) getActivity(activityId);

                            if (activity.isMatch(identifier.getId())) {
                                activityIds.add(activityId);
                            }
                        }

                        boolean activityIdsChanged = identifier.setActivityIds(activityIds);
                        if (activityIdsChanged) {
                            IdentifierEvent identifierEvent = new IdentifierEvent(identifier, activityIdsChanged,
                                    false);
                            identifierEventsByIdentifierId.put(identifier.getId(),
                                    identifierEvent);
