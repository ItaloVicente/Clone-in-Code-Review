			break;
		case SWT_SCROLLBAR_VERTICAL_VISIBLE:
			Boolean verticalVisible = (Boolean) engine.convert(value, Boolean.class, styledText.getDisplay());
			styledTextElement.setVerticalScrollBarVisible(verticalVisible);
			break;
		case SWT_SCROLLBAR_HORIZONTAL_VISIBLE:
			Boolean horizontalVisible = (Boolean) engine.convert(value, Boolean.class, styledText.getDisplay());
			styledTextElement.setHorizontalScrollBarVisible(horizontalVisible);
			break;
		case SWT_SCROLLBAR_THEMED:
