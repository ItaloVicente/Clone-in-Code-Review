
				@Override
				public boolean belongsTo(Object family) {
					if (JobFamilies.DISCARD_CHANGES.equals(family)) {
						return true;
					}
					return super.belongsTo(family);
