		if (currentWalk != null) {
			currentWalk.release();
			currentWalk = null;
		}
		if (refsListener != null) {
			refsListener.remove();
			refsListener = null;
		}
