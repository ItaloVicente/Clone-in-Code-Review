        styledText.addDisposeListener(e -> {
		    handCursor.dispose();
		    handCursor = null;
		    busyCursor.dispose();
		    busyCursor = null;
		});
