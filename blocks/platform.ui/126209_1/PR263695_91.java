		Listener listener = event -> {
			switch (event.type) {
			case SWT.MouseDown:
				System.out.println("down");
				break;
			case SWT.MouseUp:
				System.out.println("up");
				break;
			case SWT.MouseMove:
				System.out.println("move");
				break;
			}
		};
