
			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.REMOVE_FROM_INDEX.equals(family))
					return true;

				return super.belongsTo(family);
			}
