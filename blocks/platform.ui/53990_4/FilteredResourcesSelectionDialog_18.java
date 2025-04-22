		return (o1, o2) -> {
			Collator collator = Collator.getInstance();
			IResource resource1 = (IResource) o1;
			IResource resource2 = (IResource) o2;
			String s1 = resource1.getName();
			String s2 = resource2.getName();

			int s1Dot = s1.lastIndexOf('.');
			int s2Dot = s2.lastIndexOf('.');
			String n1 = s1Dot == -1 ? s1 : s1.substring(0, s1Dot);
			String n2 = s2Dot == -1 ? s2 : s2.substring(0, s2Dot);
			int comparability = collator.compare(n1, n2);
			if (comparability != 0)
				return comparability;

			if (s1Dot != -1 || s2Dot != -1) {
				comparability = collator.compare(s1, s2);
