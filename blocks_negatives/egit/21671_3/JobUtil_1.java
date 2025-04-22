			@Override
			public boolean belongsTo(Object family) {
				if (jobFamily != null && family.equals(jobFamily))
					return true;
				return super.belongsTo(family);
			}
		};
