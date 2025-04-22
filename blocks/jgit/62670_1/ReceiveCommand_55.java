	public static List<ReceiveCommand> filter(Iterable<ReceiveCommand> in
			Result want) {
		List<ReceiveCommand> r;
		if (in instanceof Collection)
			r = new ArrayList<>(((Collection<?>) in).size());
		else
			r = new ArrayList<>();
		for (ReceiveCommand cmd : in) {
