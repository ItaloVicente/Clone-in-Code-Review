		if (control instanceof Composite) {
			Composite c = (Composite) control;
			Control[] children = c.getChildren();
			for (Control element : children) {
				readStateForAndDisable(element);
			}
		}
		states.add(new ItemState(control, control.getEnabled()));
		control.setEnabled(false);
	}
