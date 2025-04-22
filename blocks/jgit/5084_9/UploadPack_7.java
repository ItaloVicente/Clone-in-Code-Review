	public void setAdvertiseRefsHook(final AdvertiseRefsHook advertiseRefsHook) {
		if (advertiseRefsHook != null)
			this.advertiseRefsHook = advertiseRefsHook;
		else
			this.advertiseRefsHook = AdvertiseRefsHook.DEFAULT;
	}

