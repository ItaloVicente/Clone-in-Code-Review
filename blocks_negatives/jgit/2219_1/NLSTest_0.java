			@Override
			public void run() {
				try {
					NLS.setLocale(locale);
					bundle = GermanTranslatedBundle.get();
				} catch (InterruptedException e) {
					this.e = e;
				} catch (BrokenBarrierException e) {
					this.e = e;
				}
