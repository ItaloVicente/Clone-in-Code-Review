
			@Override
			public boolean belongsTo(Object family) {
				if (family.equals(JobFamilies.STAGING_VIEW_REFRESH))
					return true;
				return super.belongsTo(family);
			}

