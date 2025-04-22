		} else {
			if (element instanceof CSSStylableElement) {
				CSSStylableElement elt = (CSSStylableElement) element;
				Object widget = elt.getNativeWidget();
				if (widget instanceof Widget) {
					return (Widget) widget;
				}
