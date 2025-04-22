			String[] monthValues = new String[] {
					MessageUtil.getString("January"), MessageUtil.getString("February"), MessageUtil.getString("March"), MessageUtil.getString("April"), MessageUtil.getString("May"), MessageUtil.getString("June"), MessageUtil.getString("July"), MessageUtil.getString("August"), MessageUtil.getString("September"), MessageUtil.getString("October"), MessageUtil.getString("November"), MessageUtil.getString("December") }; //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
			return monthValues[((Integer) element).intValue()];
		}
	}

	private static ArrayList<PropertyDescriptor> descriptors;
	static {
		descriptors = new ArrayList<>();

		String[] dayValues = new String[] {
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }; //$NON-NLS-31$ //$NON-NLS-30$ //$NON-NLS-29$ //$NON-NLS-28$ //$NON-NLS-27$ //$NON-NLS-26$ //$NON-NLS-25$ //$NON-NLS-24$ //$NON-NLS-23$ //$NON-NLS-22$ //$NON-NLS-21$ //$NON-NLS-20$ //$NON-NLS-19$ //$NON-NLS-18$ //$NON-NLS-17$ //$NON-NLS-16$ //$NON-NLS-15$ //$NON-NLS-14$ //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
		ComboBoxPropertyDescriptor days = new ComboBoxPropertyDescriptor(
				P_ID_DAY, P_DAY, dayValues);
		days.setLabelProvider(new DayLabelProvider());
		descriptors.add(days);

		String[] monthValues = new String[] {
				MessageUtil.getString("January"), MessageUtil.getString("February"), MessageUtil.getString("March"), MessageUtil.getString("April"), MessageUtil.getString("May"), MessageUtil.getString("June"), MessageUtil.getString("July"), MessageUtil.getString("August"), MessageUtil.getString("September"), MessageUtil.getString("October"), MessageUtil.getString("November"), MessageUtil.getString("December") }; //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
		ComboBoxPropertyDescriptor months = new ComboBoxPropertyDescriptor(
				P_ID_MONTH, P_MONTH, monthValues);
		months.setLabelProvider(new MonthLabelProvider());
		descriptors.add(months);

		descriptors.add(new TextPropertyDescriptor(P_ID_YEAR, P_YEAR));
	}

	Birthday() {
		super();
	}

	public Birthday(int day, int month, int year) {
		super();
		setDay(Integer.valueOf(day));
		setMonth(Integer.valueOf(month));
		setYear(Integer.valueOf(year));
	}

	private Integer getDay() {
		if (day == null)
			day = DAY_DEFAULT;
		return day;
	}

	private static ArrayList<PropertyDescriptor> getDescriptors() {
		return descriptors;
	}

	@Override
