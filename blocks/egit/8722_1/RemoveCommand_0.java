
			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.REPOSITORY_DELETE.equals(family))
					return true;
				else
					return super.belongsTo(family);
			}
