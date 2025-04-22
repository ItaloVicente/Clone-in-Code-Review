		Collections.sort(commands
				Comparator<ReceiveCommand>() {
					@Override
					public int compare(ReceiveCommand a
						return a.getRefName().compareTo(b.getRefName());
					}});

		int delta = 0;
