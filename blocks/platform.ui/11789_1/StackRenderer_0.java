
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				CTabItem item = ctf.getSelection();
				if (item != null) {
					MUIElement ele = (MUIElement) item.getData(OWNING_ME);
					if (ele.getParent().getSelectedElement() == ele) {
						Control ctrl = (Control) ele.getWidget();
						if (ctrl != null) {
							ctrl.setFocus();
						}
					}
				}
			}

