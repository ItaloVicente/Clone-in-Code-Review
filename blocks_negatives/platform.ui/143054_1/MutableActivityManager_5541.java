        } else {
            Set activityIdsToUpdate = new HashSet(changedActivityIds);
            if (identifier.getActivityIds() != null) {
                activityIdsToUpdate.addAll(identifier.getActivityIds());
            }
            for (Iterator iterator = activityIdsToUpdate.iterator(); iterator
                    .hasNext();) {
                String activityId = (String) iterator.next();
                Activity activity = (Activity) getActivity(activityId);

                if (activity.isMatch(id)) {
                    activityIds.add(activityId);
               }
            }

            activityIdsChanged = identifier.setActivityIds(activityIds);

            if (advisor != null) {
            	enabled = advisor.computeEnablement(this, identifier);
            }
            enabledChanged = identifier.setEnabled(enabled);

            if (activityIdsChanged || enabledChanged) {
				return new IdentifierEvent(identifier, activityIdsChanged,
                        enabledChanged);
