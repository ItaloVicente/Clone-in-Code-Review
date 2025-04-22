
	public void doOnEntry(Part part, Consumer<UpdataStrategyEntry> action) {
		switch (part) {
		case BOTH:
			action.accept(toModel);
			action.accept(toTarget);
			break;
		case TARGET:
			action.accept(toTarget);
			break;
		case MODEL:
			action.accept(toModel);
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
