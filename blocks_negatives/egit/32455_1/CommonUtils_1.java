	/**
	 * Instance of comparator that sorts strings in ascending alphabetical and
	 * numerous order (also known as natural order), case insensitive.
	 *
	 * The comparator is guaranteed to return a non-zero value if
	 * string1.equals(String2) returns false
	 */
	public static final Comparator<String> STRING_ASCENDING_COMPARATOR = new Comparator<String>() {
		public int compare(String o1, String o2) {
			if (o1.length() == 0 || o2.length() == 0)
				return o1.length() - o2.length();

			LinkedList<String> o1Parts = splitIntoDigitAndNonDigitParts(o1);
			LinkedList<String> o2Parts = splitIntoDigitAndNonDigitParts(o2);

			Iterator<String> o2PartsIterator = o2Parts.iterator();

			for (String o1Part : o1Parts) {
				if (!o2PartsIterator.hasNext())
					return 1;

				String o2Part = o2PartsIterator.next();

				int result;

				if (Character.isDigit(o1Part.charAt(0)) && Character.isDigit(o2Part.charAt(0))) {
					o1Part = stripLeadingZeros(o1Part);
					o2Part = stripLeadingZeros(o2Part);
					result = o1Part.length() - o2Part.length();
					if (result == 0)
						result = o1Part.compareToIgnoreCase(o2Part);
				} else {
					result = o1Part.compareToIgnoreCase(o2Part);
				}

				if (result != 0)
					return result;
			}

			if (o2PartsIterator.hasNext())
				return -1;
			else {
				return o1.compareTo(o2);
			}
		}
	};

	/**
	 * Instance of comparator which sorts {@link Ref} names using
	 * {@link CommonUtils#STRING_ASCENDING_COMPARATOR}.
	 */
	public static final Comparator<Ref> REF_ASCENDING_COMPARATOR = new Comparator<Ref>() {
		public int compare(Ref o1, Ref o2) {
			return STRING_ASCENDING_COMPARATOR.compare(o1.getName(), o2.getName());
		}
	};

	/**
	 * Comparator for comparing {@link IResource} by the result of
	 * {@link IResource#getName()}.
	 */
	public static final Comparator<IResource> RESOURCE_NAME_COMPARATOR = new Comparator<IResource>() {
		public int compare(IResource r1, IResource r2) {
			return Policy.getComparator().compare(r1.getName(), r2.getName());
		}
	};

