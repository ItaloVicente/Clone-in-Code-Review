		TreeSet<String> added = new TreeSet<>();
		Set<String> deleted =
				pending.stream()
						.filter(cmd -> cmd.getType() == DELETE)
						.map(c -> c.getRefName())
						.collect(Collectors.toSet());
