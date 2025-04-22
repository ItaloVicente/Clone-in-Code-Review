			}
			if (changedObj.isVisible()) {
				if (parent.getRenderer() != null) {
					Object myParent = ((AbstractPartRenderer) parent.getRenderer())
							.getUIContainer(changedObj);
					if (myParent instanceof Composite) {
						Composite parentComp = (Composite) myParent;
						ctrl.setParent(parentComp);

						Control prevControl = null;
						for (MUIElement childME : parent.getChildren()) {
							if (childME == changedObj)
								break;
							if (childME.getWidget() instanceof Control && childME.isVisible()) {
								prevControl = (Control) childME.getWidget();
							}
						}
						if (prevControl != null)
							ctrl.moveBelow(prevControl);
						else
							ctrl.moveAbove(null);
						ctrl.getShell().layout(new Control[] { ctrl }, SWT.DEFER);
					}
