					if (addNonPartSources) {
						for (ISaveablesSource nonPartSource : saveablesList.getNonPartSources()) {
							Saveable[] saveables = nonPartSource.getSaveables();
							for (Saveable saveable : saveables) {
								if (saveable.isDirty()) {
									saveablesSet.add(saveable);
								}
							}
						}
					}
