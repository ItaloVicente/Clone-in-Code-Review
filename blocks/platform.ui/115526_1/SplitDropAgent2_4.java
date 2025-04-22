				MPartStack.class, EModelService.PRESENTATION, element -> {
					MPartStack stack = (MPartStack) element;

					if (!stack.isVisible() || !(stack.getWidget() instanceof CTabFolder)) {
						return false;
					}

					CTabFolder ctf = (CTabFolder) stack.getWidget();
					if (ctf.isDisposed()) {
						return false;
