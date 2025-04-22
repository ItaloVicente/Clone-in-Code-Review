        activityManager
                .addActivityManagerListener(new IActivityManagerListener() {
                    @Override
					public void activityManagerChanged(
                            ActivityManagerEvent activityManagerEvent) {
                        switch (listenerType) {
                        case ENABLED_ACTIVITYIDS_CHANGED:
                            assertTrue(activityManagerEvent
                                    .haveEnabledActivityIdsChanged());
                            break;
                        case DEFINED_CATEGORYIDS_CHANGED:
                            assertTrue(activityManagerEvent
                                    .haveDefinedCategoryIdsChanged());
                            break;
                        case DEFINED_ACTIVITYIDS_CHANGED:
                            assertTrue(activityManagerEvent
                                    .haveDefinedActivityIdsChanged());
                            break;
                        }
                        listenerType = -1;
                    }
                });
