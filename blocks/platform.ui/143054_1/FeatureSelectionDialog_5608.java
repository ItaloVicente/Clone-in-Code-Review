				return coll.compare(name1, name2);
			}
		});

		for (AboutInfo feature : features) {
			if (feature.getFeatureId().equals(primaryFeatureId)) {
				setInitialSelection(feature);
				return;
			}
		}
	}

	@Override
