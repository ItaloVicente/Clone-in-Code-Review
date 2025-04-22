                () -> {
				    viewer.getControl().setRedraw(false);
				    viewer.refresh(false);
				    viewer.getControl().setRedraw(true);
				    updateStatusMessage();
				    updateTitle();
				});
