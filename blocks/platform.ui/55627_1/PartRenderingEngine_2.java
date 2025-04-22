			} else {
				Object parentelement = changedElement.getTransientData().get(RENDER_PARENT);
				if (parentelement != null) {
					Object widget = changedElement.getWidget();
					if (widget != null) {
						if (parentelement instanceof Control) {
							if (widget instanceof Menu) {
								if (((Menu) widget).equals(((Control) parentelement).getMenu())) {
									((Control) parentelement).setMenu(null);
								}
							}
						}
					}
				}
