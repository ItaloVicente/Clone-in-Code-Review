        if (propKey.equals(P_ID_DAY))
            return Integer.valueOf(getDay().intValue() - 1);
        if (propKey.equals(P_ID_MONTH))
            return Integer.valueOf(getMonth().intValue() - 1);
        if (propKey.equals(P_ID_YEAR))
            return getYear().toString();
        return null;
    }

    /**
     * Returns the year
     */
    private Integer getYear() {
        if (year == null)
            year = YEAR_DEFAULT;
        return year;
    }

    /* (non-Javadoc)
     * Method declared on IPropertySource
     */
    @Override
