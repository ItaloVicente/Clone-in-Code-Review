			if (isVisible) {
				Stream.of(managerForModel.getItems()).filter(i -> i.isVisible()).findFirst().ifPresent((i) -> {
					managerForModel.getControl().getDisplay().asyncExec(() -> {
						MWindow window = getWindow();
						if (window != null) {
							Object widget = window.getWidget();
							if (widget instanceof Shell) {
								((Shell) widget).requestLayout();
							}
						}
					});
				});
			}
		}
	}

	private MWindow getWindow() {
		EObject n = (EObject) toolbarModel;
		while (n.eContainer() != null) {
			n = n.eContainer();
			if (n instanceof MWindow) {
				return (MWindow) n;
			}
