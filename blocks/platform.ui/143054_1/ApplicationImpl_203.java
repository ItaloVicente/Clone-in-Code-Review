			children = new EObjectContainmentWithInverseEList<MWindow>(MWindow.class, this,
					ApplicationPackageImpl.APPLICATION__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) {
				private static final long serialVersionUID = 1L;

				@Override
				public Class<?> getInverseFeatureClass() {
					return MUIElement.class;
				}
			};
