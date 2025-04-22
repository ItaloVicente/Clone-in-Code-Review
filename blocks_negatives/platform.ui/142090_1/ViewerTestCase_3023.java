	    fDisplay = Display.getCurrent();
	    if (fDisplay == null) {
	        fDisplay = new Display();
	    }
	    fShell = new Shell(fDisplay, getShellStyle());
	    fShell.setSize(500, 500);
	    fShell.setLayout(new FillLayout());
	    fViewer = createViewer(fShell);
	    fViewer.setUseHashlookup(true);
	    setInput();
	    fShell.open();
