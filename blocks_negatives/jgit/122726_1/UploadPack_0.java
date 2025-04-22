		try {
			advertiseRefsHook.advertiseRefs(this);
		} catch (ServiceMayNotContinueException fail) {
			if (fail.getMessage() != null) {
				fail.setOutput();
			}
			throw fail;
		}

