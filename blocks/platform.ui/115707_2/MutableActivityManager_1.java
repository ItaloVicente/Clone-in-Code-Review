			deferredIdentifierJob = Job.create("Activity Identifier Update", (IJobFunction) monitor -> { //$NON-NLS-1$
				final Map identifierEventsByIdentifierId = new HashMap();

				while (!deferredIdentifiers.isEmpty()) {
					Identifier identifier = (Identifier) deferredIdentifiers.remove(0);
					Set activityIds = new HashSet();
					for (Iterator iterator = definedActivityIds.iterator(); iterator.hasNext();) {
						String activityId = (String) iterator.next();
						Activity activity = (Activity) getActivity(activityId);

						if (activity.isMatch(identifier.getId())) {
							activityIds.add(activityId);
