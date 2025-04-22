		if (P_ID_DAY.equals(property))
			return getDay() != DAY_DEFAULT;
		if (P_ID_MONTH.equals(property))
			return getMonth() != MONTH_DEFAULT;
		if (P_ID_YEAR.equals(property))
			return getYear() != YEAR_DEFAULT;
		return false;
	}

	@Override
