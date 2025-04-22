        fButton.addDisposeListener(event -> {
		    if (fImage != null) {
		        fImage.dispose();
		        fImage = null;
		    }
		    if (fColor != null) {
		        fColor.dispose();
		        fColor = null;
		    }
		});
