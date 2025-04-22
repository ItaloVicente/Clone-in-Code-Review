                .addActivityManagerListener(new IActivityManagerListener() {
                    @Override
					public void activityManagerChanged(
                            ActivityManagerEvent activityManagerEvent) {
                        ActivityManagerEvent proxyActivityManagerEvent = new ActivityManagerEvent(
                                ProxyActivityManager.this, activityManagerEvent
                                        .haveDefinedActivityIdsChanged(),
                                activityManagerEvent
                                        .haveDefinedCategoryIdsChanged(),
                                activityManagerEvent
                                        .haveEnabledActivityIdsChanged(),
                                activityManagerEvent
                                        .getPreviouslyDefinedActivityIds(),
                                activityManagerEvent
                                        .getPreviouslyDefinedCategoryIds(),
                                activityManagerEvent
                                        .getPreviouslyEnabledActivityIds());
                        fireActivityManagerChanged(proxyActivityManagerEvent);
                    }
                });
