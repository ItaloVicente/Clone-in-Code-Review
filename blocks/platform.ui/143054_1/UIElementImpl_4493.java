		case UiPackageImpl.UI_ELEMENT__WIDGET:
			setWidget(newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__RENDERER:
			setRenderer(newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED:
			setToBeRendered((Boolean) newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__ON_TOP:
			setOnTop((Boolean) newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__VISIBLE:
			setVisible((Boolean) newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__PARENT:
			setParent((MElementContainer<MUIElement>) newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__CONTAINER_DATA:
			setContainerData((String) newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF:
			setCurSharedRef((MPlaceholder) newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN:
			setVisibleWhen((MExpression) newValue);
			return;
		case UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE:
			setAccessibilityPhrase((String) newValue);
			return;
