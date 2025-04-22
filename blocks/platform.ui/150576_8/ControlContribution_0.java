
	@Override
	public void update() {
		if (ti != null && !ti.isDisposed()) {
			Control control = ti.getControl();
			if (control != null && !control.isDisposed()) {
				ti.setWidth(computeWidth(control));
			}
		}
	}

	@Override
	public void update(String id) {
		update();
	}
