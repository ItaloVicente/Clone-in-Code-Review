
		Object widget = parent.getWidget();
		if (widget instanceof Composite) {
			Composite composite = (Composite) widget;
			if (composite.getShell() == elementCtrl.getShell()) {
				Composite temp = elementCtrl.getParent();
				while (temp != composite) {
					if (temp == null) {
						return;
					}
					temp = temp.getParent();
				}
				composite.layout(true, true);
			}
		}
