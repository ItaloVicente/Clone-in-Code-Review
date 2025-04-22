		try {
			advertiseRefsHook.advertiseRefs(this);
		} catch (ServiceMayNotContinueException fail) {
			if (fail.getMessage() != null) {
				adv.writeOne("ERR " + fail.getMessage());
				fail.setOutput();
			}
			throw fail;
		}

