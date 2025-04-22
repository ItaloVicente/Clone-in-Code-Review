		Runnable editRunnable = new Runnable() {
			@Override
			public void run() {
				disposeTextWidget();
				if (newText.length() > 0 && newText.equals(text) == false) {
					text = newText;
					runnable.run();
				}
				text = null;
