			Collections.sort(remoteConfigs, new Comparator<RemoteConfig>() {

				@Override
				public int compare(RemoteConfig first, RemoteConfig second) {
					return String.CASE_INSENSITIVE_ORDER.compare(
							first.getName(), second.getName());
				}
			});
