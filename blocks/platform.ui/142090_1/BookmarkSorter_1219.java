			return result * directions[column];
		}
		case RESOURCE: {
			String res1 = marker1.getResource().getName();
			String res2 = marker2.getResource().getName();
			int result = getComparator().compare(res1, res2);
			if (result == 0) {
