		Collections.sort(commands, new Comparator<ReceiveCommand>() {
			@Override
			public int compare(ReceiveCommand a, ReceiveCommand b) {
				return a.getRefName().compareTo(b.getRefName());
			}
		});
