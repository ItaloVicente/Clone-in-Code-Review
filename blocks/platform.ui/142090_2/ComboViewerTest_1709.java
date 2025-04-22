		Combo list = (Combo) fViewer.getControl();
		return list.getItem(at);
	}

	public static void main(String args[]) {
		junit.textui.TestRunner.run(ComboViewerTest.class);
	}

	@Override
