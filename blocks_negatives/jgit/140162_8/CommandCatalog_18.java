		Arrays.sort(r, new Comparator<CommandRef>() {
			@Override
			public int compare(CommandRef o1, CommandRef o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
