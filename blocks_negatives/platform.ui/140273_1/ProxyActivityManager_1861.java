        this.activityManager
                .addActivityManagerListener(activityManagerEvent -> {
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
               });
    }
