        return this.toString();
    }

    /**
     * Returns the month
     */
    private Integer getMonth() {
        if (month == null)
            month = MONTH_DEFAULT;
        return month;
    }

    /* (non-Javadoc)
     * Method declared on IPropertySource
     */
    @Override
