                    set.removeAll(dependencies);
                }
                else {
                    set.add(activityId);
                }
                support.setEnabledActivityIds(set);
            }
        }
    };

    /**
     * Whether we are currently saving the state of activities to the preference
     * store.
     */
    protected boolean saving = false;

    /**
     * Get the singleton instance of this class.
     *
     * @return the singleton instance of this class.
     */
    public static ActivityPersistanceHelper getInstance() {
        if (singleton == null) {
            singleton = new ActivityPersistanceHelper();
        }
        return singleton;
    }

    /**
     * Returns a set of activity IDs that depend on the provided ID in order to be enabled.
     *
     * @param activityManager the activity manager to query
     * @param activityId the activity whos dependencies should be added
     * @return a set of activity IDs
     */
