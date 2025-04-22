		Thread t = new Thread(() -> {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
			}
			int sleep = 50;
			Event event = new Event();
			event.type = SWT.MouseDown;
			event.button = 1;
			display.post(event);

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e2) {
			}
			event = new Event();
			event.type = SWT.MouseMove;
			event.x = downX;
			event.y = downY + 20;
			display.post(event);

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e3) {
			}
			System.out.println("move to target");
			event = new Event();
			event.type = SWT.MouseMove;
			event.x = upX;
			event.y = upY;
			display.post(event);

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e4) {
			}
			System.out.println("move inside target");
			event = new Event();
			event.type = SWT.MouseMove;
			event.x = upX;
			event.y = upY + 20;
			display.post(event);

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e5) {
			}
			System.out.println("release");
			event = new Event();
			event.type = SWT.MouseUp;
			event.button = 1;
			display.post(event);
		});
