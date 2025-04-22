	@Override
	public void switchPerspective(String perspectiveId) {
		List<MPerspective> result = modelService.findElements(getWindow(), perspectiveId, MPerspective.class, null);
		if (!result.isEmpty()) {
			switchPerspective(result.get(0));
			return;
		}
		logger.error("Perspective with ID " + perspectiveId + " not found in the current window."); //$NON-NLS-1$ //$NON-NLS-2$
	}

