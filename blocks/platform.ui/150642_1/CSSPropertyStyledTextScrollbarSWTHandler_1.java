		} else if (SWT_SCROLLBAR_WIDTH.equals(property) && value instanceof Measure) {
			Measure measure = (Measure) value;
			int width = (int) measure.getFloatValue(LexicalUnit.SAC_PIXEL);
			styledTextElement.setScrollBarWidth(width);
		} else if (SWT_SCROLLBAR_MOUSE_NEAR_SCROLL_WIDTH.equals(property) && value instanceof Measure) {
			Measure measure = (Measure) value;
			int width = (int) measure.getFloatValue(LexicalUnit.SAC_PIXEL);
			styledTextElement.setMouseNearScrollScrollBarWidth(width);
		} else if (SWT_SCROLLBAR_BORDER_RADIUS.equals(property) && value instanceof Measure) {
			Measure measure = (Measure) value;
			int radius = (int) measure.getFloatValue(LexicalUnit.SAC_PIXEL);
			styledTextElement.setScrollBarBorderRadius(radius);
