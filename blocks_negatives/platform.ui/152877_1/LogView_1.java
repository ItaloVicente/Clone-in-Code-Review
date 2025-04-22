		Comparator<LogEntry> dateComparator = (o1, o2) -> {
			Date l1 = o1.getDate();
			Date l2 = o2.getDate();
			if ((l1 != null) && (l2 != null)) {
				return l1.before(l2) ? -1 : 1;
			} else if ((l1 == null) && (l2 == null)) {
				return 0;
			} else
				return (l1 == null) ? -1 : 1;
