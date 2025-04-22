
			@Override
			public boolean belongsTo(Object family) {
				if (family.equals(JobFamilies.DISCARD_CHANGES))
					return true;
				return super.belongsTo(family);
			}
