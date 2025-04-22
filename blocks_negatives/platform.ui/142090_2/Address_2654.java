                if (value == null)

                String testPostalCode = ((String) value).toUpperCase();
                final int length = testPostalCode.length();
                final char space = ' ';

                StringBuilder postalCodeBuffer = new StringBuilder(6);
                char current;
                for (int i = 0; i < length; i++) {
                    current = testPostalCode.charAt(i);
                    if (current != space)
                        postalCodeBuffer.append(current);
                }
                testPostalCode = postalCodeBuffer.toString();

                if (testPostalCode.length() != 6) {
                }

                if (testPostalCode.charAt(1) < '0'
                        || testPostalCode.charAt(1) > '9'
                        || testPostalCode.charAt(3) < '0'
                        || testPostalCode.charAt(3) > '9'
                        || testPostalCode.charAt(5) < '0'
                        || testPostalCode.charAt(5) > '9'
                        || testPostalCode.charAt(0) < 'A'
                        || testPostalCode.charAt(0) > 'Z'
                        || testPostalCode.charAt(2) < 'A'
                        || testPostalCode.charAt(2) > 'Z'
                        || testPostalCode.charAt(4) < 'A'
                        || testPostalCode.charAt(4) > 'Z') {
                    return MessageUtil
                            .format(
                                    "_is_an_invalid_format_for_a_postal_code", new Object[] { testPostalCode }); //$NON-NLS-1$
                }

                return null;
            }
        });
        descriptors.addElement(propertyDescriptor);

        ComboBoxPropertyDescriptor desc = new ComboBoxPropertyDescriptor(
                P_ID_PROVINCE, P_PROVINCE, provinceValues);
        desc.setLabelProvider(new ProvinceLabelProvider());
        descriptors.addElement(desc);
    }

    /**
     * Address Default Constructor
     */
    Address() {
        super();
    }

    /**
     * Creates a new address.
     *
     * @param street the street
     * @param city the city
     * @param province the province
     * @param postalCode has the form XYXYXY: where X is a letter and Y is a digit
     * @exception IllegalArgumentException, if postalcode not in above form
     */
    public Address(StreetAddress street, String city, Integer province,
            String postalCode) {
        super();
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
        setProvince(province);
    }

    /**
     * Returns the city
     */
    private String getCity() {
        if (city == null)
            city = CITY_DEFAULT;
        return city;
    }

    /**
     * Standard Accessor
     */
    private static Vector<PropertyDescriptor> getDescriptors() {
        return descriptors;
    }

    @Override
