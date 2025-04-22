        descImageCanvas.addDisposeListener(e -> {
		    for (Iterator i = imageTable.values().iterator(); i.hasNext();) {
		        ((Image) i.next()).dispose();
		    }
		    imageTable.clear();
		});
