		int timeOut = getTimeout();
		if (timeOut != -1) {
			int effTimeOut = timeOut * 1000;
			conn.setConnectTimeout(effTimeOut);
			conn.setReadTimeout(effTimeOut);
		}
