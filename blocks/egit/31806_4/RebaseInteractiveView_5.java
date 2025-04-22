				planTreeViewer.getTree().setRedraw(false);
				try {
					planTreeViewer.setInput(currentPlan);
					refreshUI();
				} finally {
					planTreeViewer.getTree().setRedraw(true);
				}
