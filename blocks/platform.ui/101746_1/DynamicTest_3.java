		category_to_listen.addCategoryListener(categoryEvent -> {
			switch (listenerType) {
			case DEFINED_CHANGED:
				assertTrue(categoryEvent.hasDefinedChanged());
				break;
			case NAME_CHANGED:
				assertTrue(categoryEvent.hasNameChanged());
				break;
			case PATTERN_BINDINGS_CHANGED:
				assertTrue(categoryEvent.haveCategoryActivityBindingsChanged());
				break;
			case DESCRIPTION_CHANGED:
				break;
			}
			listenerType = -1;
		});
