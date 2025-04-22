				boolean lock = !HandlerUtil.toggleCommandState(event.getCommand());
				final List<MToolBar> children = modelService.findElements(winModel, null, MToolBar.class, null);
				for (MToolBar el : children) {
					if (!el.getTags().contains(TOOLBAR_SEPARATOR)) {
						if (lock) {
							if (!el.getTags().contains(IPresentationEngine.NO_MOVE)) {
								el.getTags().add(IPresentationEngine.NO_MOVE);
							}
							if (el.getTags().contains(IPresentationEngine.DRAGGABLE)) {
								el.getTags().remove(IPresentationEngine.DRAGGABLE);
							}
						} else {
							if (el.getTags().contains(IPresentationEngine.NO_MOVE)) {
								el.getTags().remove(IPresentationEngine.NO_MOVE);
							}
							if (!el.getTags().contains(IPresentationEngine.DRAGGABLE)) {
								el.getTags().add(IPresentationEngine.DRAGGABLE);
							}
						}
						el.setToBeRendered(false);
						el.setToBeRendered(true);
					}
				}
