            String[] values = new String[] { P_VALUE_TRUE_LABEL,
                    P_VALUE_FALSE_LABEL };
            return values[((Integer) element).intValue()];
        }
    }

    static private ArrayList<PropertyDescriptor> descriptors;
    static {
        descriptors = new ArrayList<>();
        PropertyDescriptor propertyDescriptor;

        propertyDescriptor = new TextPropertyDescriptor(P_ID_PHONENUMBER,
                P_PHONENUMBER);
        propertyDescriptor.setCategory(P_CONTACTINFO);
        propertyDescriptor.setHelpContextIds(PHONE_NUMBER_CONTEXT);
        descriptors.add(propertyDescriptor);

        propertyDescriptor = new PropertyDescriptor(P_ID_ADDRESS, P_ADDRESS);
        propertyDescriptor.setCategory(P_CONTACTINFO);
        propertyDescriptor.setHelpContextIds(ADDRESS_CONTEXT);
        descriptors.add(propertyDescriptor);

        propertyDescriptor = new TextPropertyDescriptor(P_ID_EMAIL, P_EMAIL);
        propertyDescriptor.setCategory(P_CONTACTINFO);
        propertyDescriptor.setHelpContextIds(EMAIL_CONTEXT);
        propertyDescriptor.setValidator(new EmailAddressValidator());
        descriptors.add(propertyDescriptor);

        propertyDescriptor = new TextPropertyDescriptor(P_ID_FULLNAME,
                P_FULLNAME);
        propertyDescriptor.setCategory(P_PERSONELINFO);
        propertyDescriptor.setHelpContextIds(FULL_NAME_CONTEXT);
        descriptors.add(propertyDescriptor);

        propertyDescriptor = new PropertyDescriptor(P_ID_BDAY, P_BDAY);
        propertyDescriptor.setCategory(P_PERSONELINFO);
        propertyDescriptor.setHelpContextIds(BIRTHDAY_CONTEXT);
        descriptors.add(propertyDescriptor);

        propertyDescriptor = new ComboBoxPropertyDescriptor(P_ID_COOP, P_COOP,
                new String[] { P_VALUE_TRUE_LABEL, P_VALUE_FALSE_LABEL });
        propertyDescriptor.setCategory(P_PERSONELINFO);
        propertyDescriptor.setHelpContextIds(COOP_CONTEXT);
        propertyDescriptor.setLabelProvider(new BooleanLabelProvider());
        descriptors.add(propertyDescriptor);

        propertyDescriptor = new TextPropertyDescriptor(P_ID_SALARY, P_SALARY);
        propertyDescriptor.setHelpContextIds(new Object[] { SALARY_CONTEXT });
        propertyDescriptor
                .setFilterFlags(new String[] { IPropertySheetEntry.FILTER_ID_EXPERT });
        propertyDescriptor.setValidator(value -> {
		    if (value == null)
		    Float trySalary;
		    try {
		        trySalary = Float.valueOf(Float.parseFloat((String) value));
		    } catch (NumberFormatException e) {
		    }
		    if (trySalary.floatValue() < 0.0)
		        return MessageUtil
		    return null;
