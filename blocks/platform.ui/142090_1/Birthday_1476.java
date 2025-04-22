			String[] dayValues = new String[] {
					"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }; //$NON-NLS-31$ //$NON-NLS-30$ //$NON-NLS-29$ //$NON-NLS-28$ //$NON-NLS-27$ //$NON-NLS-26$ //$NON-NLS-25$ //$NON-NLS-24$ //$NON-NLS-23$ //$NON-NLS-22$ //$NON-NLS-21$ //$NON-NLS-20$ //$NON-NLS-19$ //$NON-NLS-18$ //$NON-NLS-17$ //$NON-NLS-16$ //$NON-NLS-15$ //$NON-NLS-14$ //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
			return dayValues[((Integer) element).intValue()];
		}
	}

	static private class MonthLabelProvider extends LabelProvider {
		@Override
