
			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.ADD_TO_INDEX.equals(family))
					return true;

				return super.belongsTo(family);
			}
