
		if (element instanceof MPerspective) {
			MPerspective perspective = (MPerspective) element;
			for (MWindow window : perspective.getWindows()) {
				if (window.isToBeRendered()) {
					count++;
				}
			}
		}
