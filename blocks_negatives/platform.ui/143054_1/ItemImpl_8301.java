			case MenuPackageImpl.ITEM__LABEL:
				setLabel((String)newValue);
				return;
			case MenuPackageImpl.ITEM__ICON_URI:
				setIconURI((String)newValue);
				return;
			case MenuPackageImpl.ITEM__TOOLTIP:
				setTooltip((String)newValue);
				return;
			case MenuPackageImpl.ITEM__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case MenuPackageImpl.ITEM__SELECTED:
				setSelected((Boolean)newValue);
				return;
			case MenuPackageImpl.ITEM__TYPE:
				setType((ItemType)newValue);
				return;
