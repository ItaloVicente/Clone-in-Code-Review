                return coll.compare(name1, name2);
            }
        });

        for (AboutInfo feature : features) {
            if (feature.getFeatureId().equals(primaryFeatureId)) {
				setInitialSelections(feature);
                return;
            }
        }

        setInitialSelections(new Object[0]);
    }

    @Override
