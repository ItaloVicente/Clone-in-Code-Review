		if (window instanceof WorkbenchWindow) {
			EPartService partService = null;
			try {
				partService = ((WorkbenchWindow) window).getModel().getContext().get(EPartService.class);
			} catch (Exception e) {
			}
			if (partService != null) {
				partService.saveAll(false);
			}
		}
