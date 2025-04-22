        StringBuilder outStringBuilder = new StringBuilder();
        final String comma_space = ", "; //$NON-NLS-1$
        if (!getStreet().equals(STREET_DEFAULT)) {
            outStringBuilder.append(getStreet());
            outStringBuilder.append(comma_space);
        }

        outStringBuilder.append(getCity());
        outStringBuilder.append(space);
        outStringBuilder.append(provinceValues[getProvince().intValue()]);
        outStringBuilder.append(comma_space);
        outStringBuilder.append(getPostalCode());

        return outStringBuilder.toString();
    }
