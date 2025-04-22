			} else {
				ISaveablePart saveable = SaveableHelper.getSaveable(part);
				if (saveable != null) {
					if (saveable.isDirty()) {
						result.add(part);
					}
				}
