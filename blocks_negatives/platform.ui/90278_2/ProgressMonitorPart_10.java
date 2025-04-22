        	fStopButton.addSelectionListener(new SelectionAdapter() {
        		@Override
				public void widgetSelected(SelectionEvent e) {
        			setCanceled(true);
        			if (fStopButton != null) {
        				fStopButton.setEnabled(false);
        			}
        		}
        	});
