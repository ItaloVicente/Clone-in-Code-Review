			children = new EObjectContainmentWithInverseEList<MToolBarElement>(MToolBarElement.class, this,
					MenuPackageImpl.TOOL_BAR_CONTRIBUTION__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) {
				private static final long serialVersionUID = 1L;

				@Override
				public Class<?> getInverseFeatureClass() {
					return MUIElement.class;
				}
			};
