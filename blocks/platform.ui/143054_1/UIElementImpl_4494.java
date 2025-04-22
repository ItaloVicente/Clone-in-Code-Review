		case UiPackageImpl.UI_ELEMENT__WIDGET:
			setWidget(WIDGET_EDEFAULT);
			return;
		case UiPackageImpl.UI_ELEMENT__RENDERER:
			setRenderer(RENDERER_EDEFAULT);
			return;
		case UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED:
			setToBeRendered(TO_BE_RENDERED_EDEFAULT);
			return;
		case UiPackageImpl.UI_ELEMENT__ON_TOP:
			setOnTop(ON_TOP_EDEFAULT);
			return;
		case UiPackageImpl.UI_ELEMENT__VISIBLE:
			setVisible(VISIBLE_EDEFAULT);
			return;
		case UiPackageImpl.UI_ELEMENT__PARENT:
			setParent((MElementContainer<MUIElement>) null);
			return;
		case UiPackageImpl.UI_ELEMENT__CONTAINER_DATA:
			setContainerData(CONTAINER_DATA_EDEFAULT);
			return;
		case UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF:
			setCurSharedRef((MPlaceholder) null);
			return;
		case UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN:
			setVisibleWhen((MExpression) null);
			return;
		case UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE:
			setAccessibilityPhrase(ACCESSIBILITY_PHRASE_EDEFAULT);
			return;
