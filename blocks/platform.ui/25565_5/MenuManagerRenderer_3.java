					boolean prevDisposingMenuUIElement = disposingMenuUIElement;
					try {
						disposingMenuUIElement = true;
						cleanUp(menuModel);
					} finally {
						disposingMenuUIElement = prevDisposingMenuUIElement;
					}
