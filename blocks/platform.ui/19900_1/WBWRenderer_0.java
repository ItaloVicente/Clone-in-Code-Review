
			shell.addListener(SWT.Deactivate, new Listener() {
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					updateNonFocusState(SWT.Deactivate, w);
				}
			});
		}
	}

	private void updateNonFocusState(int event, MWindow win) {
		MPerspective perspective = modelService.getActivePerspective(win);
		if (perspective == null) {
			return;
		}

		List<MPartStack> stacks = modelService.findElements(perspective, null,
				MPartStack.class, Arrays.asList(CSSConstants.CSS_ACTIVE_CLASS));
		if (stacks.isEmpty()) {
			return;
		}

		MPartStack stack = stacks.get(0);
		int tagsCount = stack.getTags().size();
		boolean hasNonFocusTag = stack.getTags().contains(
				CSSConstants.CSS_NON_FOCUS_CLASS);

		if (event == SWT.Activate && hasNonFocusTag) {
			stack.getTags().remove(CSSConstants.CSS_NON_FOCUS_CLASS);
		} else if (event == SWT.Deactivate && !hasNonFocusTag) {
			stack.getTags().add(CSSConstants.CSS_NON_FOCUS_CLASS);
		}
		if (tagsCount != stack.getTags().size()) {
			setCSSInfo(stack, stack.getWidget());
