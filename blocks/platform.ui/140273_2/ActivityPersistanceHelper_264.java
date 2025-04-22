					set.removeAll(dependencies);
				} else {
					set.add(activityId);
				}
				support.setEnabledActivityIds(set);
			}
		}
	};

	protected boolean saving = false;

	public static ActivityPersistanceHelper getInstance() {
		if (singleton == null) {
			singleton = new ActivityPersistanceHelper();
		}
		return singleton;
	}

