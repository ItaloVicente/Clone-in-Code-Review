			provider.setSelection(selection);
			setTopIndex(topIndex);
			if (parent instanceof Composite) {
				Composite composite = (Composite) parent;
				composite.layout(true);
			}
			parent.setRedraw(true);
		} else {
			styledText.setFont(font);
			if (verticalRuler instanceof IVerticalRulerExtension) {
				IVerticalRulerExtension e = (IVerticalRulerExtension) verticalRuler;
				e.setFont(font);
			}
		}
