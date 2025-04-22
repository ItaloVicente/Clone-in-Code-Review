		fViewer.getTree().addListener(SWT.Collapse, event -> {
			if (OPTION1) {
				final long start = System.currentTimeMillis();
				fViewer.setInput(new Node(0, 0, null));
				final long end = System.currentTimeMillis();
				System.out.println("setInput took: " + (end - start) + " ms");
			} else {
				final long start2 = System.currentTimeMillis();
				fViewer.getTree().removeAll();
				final long end2 = System.currentTimeMillis();
				System.out.println("removeAll took: " + (end2 - start2) + " ms");
			}

		});

