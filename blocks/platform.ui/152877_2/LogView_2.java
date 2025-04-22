		Comparator<AbstractEntry> dateComparator = (o1, o2) -> {
			if (o1 instanceof LogEntry && o2 instanceof LogEntry) {
				Date l1 = ((LogEntry)o1).getDate();
				Date l2 = ((LogEntry)o2).getDate();
				if ((l1 != null) && (l2 != null)) {
					return l1.before(l2) ? -1 : 1;
				} else if ((l1 == null) && (l2 == null)) {
					return 0;
				} else
					return (l1 == null) ? -1 : 1;
				}
			return 0;
