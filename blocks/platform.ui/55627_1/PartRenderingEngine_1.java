				} else {
					Object parentelement = changedElement.getTransientData().get(RENDER_PARENT);
					if (parentelement != null) {
						Object widget = changedElement.getWidget();
						if (widget != null) {
							if (parentelement instanceof Control) {
								if (widget instanceof Menu) {
									if (((Control) parentelement).getMenu() == null) {
										((Control) parentelement).setMenu((Menu) widget);
									}
								}
							}
						}
					}
