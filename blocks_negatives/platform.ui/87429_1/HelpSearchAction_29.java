        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
            	workbenchWindow.getWorkbench().getHelpSystem().displaySearch();
            }
        });
