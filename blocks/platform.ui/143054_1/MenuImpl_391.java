			children = new EObjectContainmentWithInverseEList<MMenuElement>(MMenuElement.class, this,
					MenuPackageImpl.MENU__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) {
				private static final long serialVersionUID = 1L;

				@Override
				public Class<?> getInverseFeatureClass() {
					return MUIElement.class;
				}
			};
