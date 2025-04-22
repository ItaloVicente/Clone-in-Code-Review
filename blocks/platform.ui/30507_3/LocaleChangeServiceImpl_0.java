			if (element instanceof MPart) {
				MPart mPart = (MPart) element;
				MToolBar toolbar = mPart.getToolbar();
				if (toolbar != null && toolbar.getChildren() != null) {
					toolbar.updateLocalization();
					updateLocalization(toolbar.getChildren());
				}
			}

