			return new ThingWithId() {
				@Override
				public String getUniqueId() {
					return assignedIds.get(adaptableObject);
				}
			};
