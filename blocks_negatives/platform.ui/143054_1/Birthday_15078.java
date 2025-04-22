        if (P_ID_DAY.equals(property)) {
            setDay(DAY_DEFAULT);
            return;
        }
        if (P_ID_MONTH.equals(property)) {
            setMonth(MONTH_DEFAULT);
            return;
        }
        if (P_ID_YEAR.equals(property)) {
            setYear(YEAR_DEFAULT);
            return;
        }
    }

    /**
     * Sets the day
     */
    private void setDay(Integer newDay) {
        day = newDay;
    }

    /**
     * Sets the month
     */
    private void setMonth(Integer newMonth) {
        month = newMonth;
    }

    /**
     * The <code>Birthday</code> implementation of this
     * <code>IPropertySource</code> method
     * defines the following Setable properties
     *
     * 	1) P_DAY expects java.lang.Integer
     * 	2) P_MONTH expects java.lang.Integer
     *  3) P_YEAR expects java.lang.Integer
     */
    @Override
