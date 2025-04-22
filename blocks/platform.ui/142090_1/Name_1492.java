	static private ArrayList<TextPropertyDescriptor> descriptors;
	static {
		descriptors = new ArrayList<>();
		descriptors.add(new TextPropertyDescriptor(P_ID_FIRSTNAME,
				P_FIRSTNAME));
		descriptors.add(new TextPropertyDescriptor(P_ID_LASTNAME,
				P_LASTNAME));
		descriptors.add(new TextPropertyDescriptor(P_ID_MIDDLENAME,
				P_MIDDLENAME));
	}
