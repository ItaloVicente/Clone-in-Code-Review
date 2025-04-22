		super.setUp();
		red = new Color(Display.getCurrent(), 255, 0, 0);
		green = new Color(Display.getCurrent(), 0, 255, 0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TableColorProviderTest.class);
	}

	@Override
