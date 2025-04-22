	private boolean isValid(MUIElement element) {
		if (element == null || !element.isToBeRendered()) {
			return false;
		}

		if (element instanceof MApplication) {
			return true;
		}

		MUIElement parent = element.getParent();
		if (parent == null && element instanceof MWindow) {
			parent = (MUIElement) ((EObject) element).eContainer();
		}

		if (parent == null) {
			MWindow window = modelService.getTopLevelWindowFor(element);
			return window == null ? false : isValid(modelService
					.findPlaceholderFor(window, element));
		}

		return isValid(parent);
	}

	synchronized private void activateStack(MElementContainer<MUIElement> stack) {
		if (stack == null || !(stack.getWidget() instanceof CTabFolder))
			return;

		CTabFolder ctf = (CTabFolder) stack.getWidget();
		if (ctf == null || ctf.isDisposed())
			return;

		if (activationJob == null) {
			activationJob = new ActivationJob();
			activationJob.stackToActivate = stack;
			ctf.getDisplay().asyncExec(activationJob);
		} else {
			activationJob.stackToActivate = stack;
		}
	}

