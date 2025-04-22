	public static void main(String[] args) {
		final Display display = new Display();

		Realm.runWithDefault(DisplayRealm.getRealm(display), () -> {
			Shell shell = createShell();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		});

		display.dispose();
	}

	private static Shell createShell() {
		Shell shell = new Shell();
		shell.setLayout(new GridLayout(3, false));
