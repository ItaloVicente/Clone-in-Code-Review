        activity_to_listen.addActivityListener(new IActivityListener() {
            @Override
			public void activityChanged(ActivityEvent activityEvent) {
                switch (listenerType) {
                case DEFINED_CHANGED:
                    assertTrue(activityEvent.hasDefinedChanged());
                    break;
                case ENABLED_CHANGED:
                    assertTrue(activityEvent.hasEnabledChanged());
                    break;
                case NAME_CHANGED:
                    assertTrue(activityEvent.hasNameChanged());
                    break;
                case PATTERN_BINDINGS_CHANGED:
                    assertTrue(activityEvent
                            .haveActivityPatternBindingsChanged());
                    break;
                case ACTIVITY_ACTIVITY_BINDINGS_CHANGED:
                    assertTrue(activityEvent
                            .haveActivityRequirementBindingsChanged());
                    break;
                case DESCRIPTION_CHANGED:
                    assertTrue(activityEvent.hasDescriptionChanged());
                    break;
                case DEFAULT_ENABLED_CHANGED:
                    assertTrue(activityEvent.hasDefaultEnabledChanged());
                    break;
                }
                listenerType = -1;
            }
        });
