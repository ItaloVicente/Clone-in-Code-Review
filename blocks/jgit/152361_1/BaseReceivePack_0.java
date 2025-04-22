	}

	public void sendAdvertisedRefsWithExceptionPropagation(RefAdvertiser adv)
			throws IOException
		if (advertiseError != null) {
			return;
		}

		advertiseRefsHook.advertiseRefs(this);
