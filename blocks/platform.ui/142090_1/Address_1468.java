			String[] provinceValues = new String[] {
					MessageUtil.getString("British_Columbia"), MessageUtil.getString("Alberta"), MessageUtil.getString("Saskatchewan"), MessageUtil.getString("Manitoba"), MessageUtil.getString("Ontario"), MessageUtil.getString("Quebec"), MessageUtil.getString("Newfoundland"), MessageUtil.getString("Prince_Edward_Island"), MessageUtil.getString("Nova_Scotia"), MessageUtil.getString("New_Brunswick"), MessageUtil.getString("Yukon"), MessageUtil.getString("North_West_Territories"), MessageUtil.getString("Nunavut") }; //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
			return provinceValues[((Integer) element).intValue()];
		}
	}

	private static Vector<PropertyDescriptor> descriptors;

	private static String[] provinceValues;
	static {
		descriptors = new Vector<>();
		provinceValues = new String[] {
				MessageUtil.getString("British_Columbia"), MessageUtil.getString("Alberta"), MessageUtil.getString("Saskatchewan"), MessageUtil.getString("Manitoba"), MessageUtil.getString("Ontario"), MessageUtil.getString("Quebec"), MessageUtil.getString("Newfoundland"), MessageUtil.getString("Prince_Edward_Island"), MessageUtil.getString("Nova_Scotia"), MessageUtil.getString("New_Brunswick"), MessageUtil.getString("Yukon"), MessageUtil.getString("North_West_Territories"), MessageUtil.getString("Nunavut") }; //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
		descriptors.addElement(new PropertyDescriptor(P_ID_STREET, P_STREET));
		descriptors.addElement(new TextPropertyDescriptor(P_ID_CITY, P_CITY));

		PropertyDescriptor propertyDescriptor = new TextPropertyDescriptor(
				P_ID_POSTALCODE, P_POSTALCODE);
		propertyDescriptor.setValidator(new ICellEditorValidator() {
			@Override
