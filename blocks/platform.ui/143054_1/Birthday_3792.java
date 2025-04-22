		if (P_ID_DAY.equals(name)) {
			setDay(Integer.valueOf(((Integer) value).intValue() + 1));
			return;
		}
		if (P_ID_MONTH.equals(name)) {
			setMonth(Integer.valueOf(((Integer) value).intValue() + 1));
			return;
		}
		if (P_ID_YEAR.equals(name)) {
			try {
				setYear(Integer.valueOf((String) value));
			} catch (NumberFormatException e) {
				setYear(YEAR_DEFAULT);
			}
			return;
		}
	}

	private void setYear(Integer newYear) {
		year = newYear;
	}

	@Override
