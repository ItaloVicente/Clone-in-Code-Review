			final WritableList menuItemStrings = new WritableList();
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					System.out.println("adding item");
					menuItemStrings.add(new Date().toString());
					display.timerExec(5000, this);
				}
			});
