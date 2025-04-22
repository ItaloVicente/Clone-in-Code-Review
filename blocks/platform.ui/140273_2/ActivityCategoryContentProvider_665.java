			}
			return categories.toArray();
		} else if (parentElement instanceof ICategory) {
			return getCategoryActivities((ICategory) parentElement);
		}
		return new Object[0];
	}

	@Override
