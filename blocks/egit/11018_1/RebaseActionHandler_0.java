		RebaseCurrentRefCommand rebaseCurrent = new RebaseCurrentRefCommand();
		rebaseCurrent.setEnabled(event.getApplicationContext());
		if (rebaseCurrent.isEnabled()) {
			return rebaseCurrent.execute(event);
		} else {
			return null;
		}
