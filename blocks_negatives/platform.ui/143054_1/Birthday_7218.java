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

    /**
     * Sets the year
     */
    private void setYear(Integer newYear) {
        year = newYear;
    }

    /**
     * The value as displayed in the Property Sheet.
     * @return java.lang.String
     */
    @Override
