		} else {
			if (element instanceof CSSStylableElement) {
				CSSStylableElement elt = (CSSStylableElement) element;
				Object widget = elt.getNativeWidget();
				if (widget instanceof Control) {
					return (Control) widget;
				}
