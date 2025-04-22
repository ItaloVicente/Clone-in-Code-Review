		Object owner = composite.getData(AbstractPartRenderer.OWNING_ME);
		if (owner instanceof MTrimBar) {
			MTrimBar bar = (MTrimBar) owner;
			for (MTrimElement te : bar.getChildren()) {
				hideManagedTB(te);
			}
