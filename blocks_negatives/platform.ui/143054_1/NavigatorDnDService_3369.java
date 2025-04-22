		Arrays.sort(array, new Comparator() {
			@Override
			public int compare(Object arg0, Object arg1) {
				CommonDropAdapterAssistant a = (CommonDropAdapterAssistant) arg0;
				CommonDropAdapterAssistant b = (CommonDropAdapterAssistant) arg1;
				if (a.getClass().getName().startsWith(id))
					return -1;
				if (b.getClass().getName().startsWith(id))
					return 1;
				return a.getClass().getName().compareTo(b.getClass().getName());
			}
