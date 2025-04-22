		syncExec(() -> {
			setRedraw(false);
			try {
				refreshViewersInternal();
			} finally {
				setRedraw(true);
