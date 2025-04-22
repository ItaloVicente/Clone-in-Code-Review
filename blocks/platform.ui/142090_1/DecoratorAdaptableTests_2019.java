	private String getDecorationTextFor(Object object) {
		DecoratorManager dm = getDecoratorManager();
		LightweightDecoratorManager ldm = dm.getLightweightManager();
		DecorationResult result = ldm.getDecorationResult(object);
		return result.decorateWithText("Default label");
	}
