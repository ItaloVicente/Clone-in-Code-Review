		if (addNonPartSources) {
			for (ISaveablesSource nonPartSource : getNonPartSources()) {
				Saveable[] saveables = nonPartSource.getSaveables();
				for (Saveable saveable : saveables) {
					if (saveable.isDirty()) {
						postCloseInfo.modelsClosing.add(saveable);
					}
				}
			}
		}
