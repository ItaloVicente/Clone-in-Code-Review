        	fStopButton.addDisposeListener(new DisposeListener() {
        		@Override
				public void widgetDisposed(DisposeEvent e) {
        			stopImage.dispose();
        			arrowCursor.dispose();
        		}
        	});
