			case UiPackageImpl.UI_ELEMENT__WIDGET:
				return WIDGET_EDEFAULT == null ? widget != null : !WIDGET_EDEFAULT.equals(widget);
			case UiPackageImpl.UI_ELEMENT__RENDERER:
				return RENDERER_EDEFAULT == null ? renderer != null : !RENDERER_EDEFAULT.equals(renderer);
			case UiPackageImpl.UI_ELEMENT__TO_BE_RENDERED:
				return toBeRendered != TO_BE_RENDERED_EDEFAULT;
			case UiPackageImpl.UI_ELEMENT__ON_TOP:
				return onTop != ON_TOP_EDEFAULT;
			case UiPackageImpl.UI_ELEMENT__VISIBLE:
				return visible != VISIBLE_EDEFAULT;
			case UiPackageImpl.UI_ELEMENT__PARENT:
				return getParent() != null;
			case UiPackageImpl.UI_ELEMENT__CONTAINER_DATA:
				return CONTAINER_DATA_EDEFAULT == null ? containerData != null : !CONTAINER_DATA_EDEFAULT.equals(containerData);
			case UiPackageImpl.UI_ELEMENT__CUR_SHARED_REF:
				return curSharedRef != null;
			case UiPackageImpl.UI_ELEMENT__VISIBLE_WHEN:
				return visibleWhen != null;
			case UiPackageImpl.UI_ELEMENT__ACCESSIBILITY_PHRASE:
				return ACCESSIBILITY_PHRASE_EDEFAULT == null ? accessibilityPhrase != null : !ACCESSIBILITY_PHRASE_EDEFAULT.equals(accessibilityPhrase);
			case UiPackageImpl.UI_ELEMENT__LOCALIZED_ACCESSIBILITY_PHRASE:
				return LOCALIZED_ACCESSIBILITY_PHRASE_EDEFAULT == null ? getLocalizedAccessibilityPhrase() != null : !LOCALIZED_ACCESSIBILITY_PHRASE_EDEFAULT.equals(getLocalizedAccessibilityPhrase());
