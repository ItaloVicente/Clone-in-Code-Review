            if (activityManagerEvent.haveDefinedActivityIdsChanged()) {
                Set delta = new HashSet(activityManagerEvent
                        .getActivityManager().getDefinedActivityIds());
                delta.removeAll(activityManagerEvent
                        .getPreviouslyDefinedActivityIds());
                loadEnabledStates(activityManagerEvent
                        .getActivityManager().getEnabledActivityIds(), delta);
            }
