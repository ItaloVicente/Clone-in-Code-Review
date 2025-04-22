	    Assert.isTrue(viewer != null);
	    selectedValue = value;
	    if (value == null) {
	        viewer.setSelection(StructuredSelection.EMPTY);
	    } else {
	        viewer.setSelection(new StructuredSelection(value));
	    }
