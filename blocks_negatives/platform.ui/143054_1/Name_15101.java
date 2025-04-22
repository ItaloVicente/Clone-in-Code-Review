        StringBuilder outStringBuilder = new StringBuilder();
        if (getFirstName() != FIRSTNAME_DEFAULT) {
            outStringBuilder.append(getFirstName());
        }
        if (getInitial() != MIDDLENAME_DEFAULT) {
            outStringBuilder.append(getInitial());
        }
        if (getLastName() != LASTNAME_DEFAULT) {
            outStringBuilder.append(getLastName());
        }

        return outStringBuilder.toString();
    }
