			if (changedElement.isVisible()) {
				if (changedElement.isToBeRendered()) {
					if (changedElement.getWidget() instanceof Control) {
						Composite realComp = (Composite) renderer
								.getUIContainer(changedElement);
						Control ctrl = (Control) changedElement.getWidget();
						ctrl.setParent(realComp);
						fixZOrder(changedElement);
					}
