        if (propKey.equals(P_ID_ADDRESS)) {
            return;
        }
        if (propKey.equals(P_ID_FULLNAME)) {
            setFullName(new Name((String) val));
            return;
        }
        if (propKey.equals(P_ID_PHONENUMBER)) {
            setPhoneNumber((String) val);
            return;
        }
        if (propKey.equals(P_ID_EMAIL)) {
            setEmailAddress(new EmailAddress((String) val));
            return;
        }
        if (propKey.equals(P_ID_COOP)) {
            setCoop(((Integer) val).equals(P_VALUE_TRUE) ? Boolean.TRUE
                    : Boolean.FALSE);
        }
        if (propKey.equals(P_ID_BDAY)) {
            return;
        }
        if (propKey.equals(P_ID_SALARY)) {
            try {
                setSalary(Float.valueOf(Float.parseFloat((String) val)));
            } catch (NumberFormatException e) {
                setSalary(salary_Default);
            }
            return;
        }
        if (propKey.equals(P_ID_HAIRCOLOR)) {
            setHairColor((RGB) val);
            return;
        }
        if (propKey.equals(P_ID_EYECOLOR)) {
            setEyeColor((RGB) val);
            return;
        }
        super.setPropertyValue(propKey, val);
    }

    /**
     * Sets the salary
     */
    void setSalary(Float newSalary) {
        salary = newSalary;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getForeground(java.lang.Object)
     */
    public RGB getForeground(Object element) {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getBackground(java.lang.Object)
     */
    public RGB getBackground(Object element) {
        return null;
    }
