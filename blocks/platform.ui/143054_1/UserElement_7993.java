		propertyDescriptor.setCategory(P_PERSONELINFO);
		descriptors.add(propertyDescriptor);

		propertyDescriptor = new ColorPropertyDescriptor(P_ID_HAIRCOLOR,
				P_HAIRCOLOR);
		propertyDescriptor.setCategory(P_PERSONALINFO);
		propertyDescriptor.setHelpContextIds(HAIR_COLOR__CONTEXT);
		descriptors.add(propertyDescriptor);

		propertyDescriptor = new ColorPropertyDescriptor(P_ID_EYECOLOR,
				P_EYECOLOR);
		propertyDescriptor.setCategory(P_PERSONALINFO);
		propertyDescriptor.setHelpContextIds(EYE_COLOR_CONTEXT);
		descriptors.add(propertyDescriptor);

		ArrayList<PropertyDescriptor> parentDescriptors = OrganizationElement.getDescriptors();
		for (int i = 0; i < parentDescriptors.size(); i++) {
			descriptors.add(parentDescriptors.get(i));
		}
	}

	UserElement(String name, GroupElement parent) {
		super(name, parent);
	}

	private Address getAddress() {
		if (address == null)
			address = new Address();
		return address;
	}

	private Birthday getBirthday() {
		if (birthday == null)
			birthday = new Birthday();
		return birthday;
	}

	@Override
