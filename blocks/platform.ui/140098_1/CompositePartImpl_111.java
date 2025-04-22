			children = new EObjectContainmentWithInverseEList<MPartSashContainerElement>(
					MPartSashContainerElement.class, this, BasicPackageImpl.COMPOSITE_PART__CHILDREN,
					UiPackageImpl.UI_ELEMENT__PARENT) {
				private static final long serialVersionUID = 1L;

				@Override
				public Class<?> getInverseFeatureClass() {
					return MUIElement.class;
				}
			};
