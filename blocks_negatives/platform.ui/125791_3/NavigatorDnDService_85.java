		Arrays.sort(array, (a, b) -> {
			if (a.getClass().getName().startsWith(id))
				return -1;
			if (b.getClass().getName().startsWith(id))
				return 1;
			return a.getClass().getName().compareTo(b.getClass().getName());
