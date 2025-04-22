			while (true) {
				if (!display.readAndDispatch()) {
					if (initDone[0])
						break;
					display.sleep();
				}
				
