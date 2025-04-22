		inUIThread(() -> {
			if (fProgressBar != null) {
				fProgressBar.sendRemainingWork();
				fProgressBar.done();
			}
			setMessage(null);
			hideProgress();
		});
