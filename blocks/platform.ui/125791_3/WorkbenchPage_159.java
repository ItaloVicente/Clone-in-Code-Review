        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
				ret[0] = close(true, true);
            }
        });
