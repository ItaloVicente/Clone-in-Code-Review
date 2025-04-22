
		emptyArea.setBackground(viewer.getControl().getBackground());
		if (!repositories.isEmpty())
			layout.topControl = viewer.getControl();
		else
			layout.topControl = emptyArea;
		
