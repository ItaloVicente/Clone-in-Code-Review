
		Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
		ExpandableComposite expandableComposite = (ExpandableComposite) control;
		switch (property.toLowerCase()) {
		case TITLE_BAR_FOREGROUND:
			expandableComposite.setTitleBarForeground(newColor);
			break;
		case CSSPropertyFormHandler.TB_TOGGLE:
			expandableComposite.setToggleColor(newColor);
			break;
		case CSSPropertyFormHandler.TB_TOGGLE_HOVER:
			expandableComposite.setActiveToggleColor(newColor);
			break;
		default:
			break;
