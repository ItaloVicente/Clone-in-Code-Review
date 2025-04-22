		if (rm != null)
			try {
				rm.call();
			} catch (NoFilepatternException e) {
			} catch (Exception e2) {
				Activator.error(e2.getMessage(), e2);
