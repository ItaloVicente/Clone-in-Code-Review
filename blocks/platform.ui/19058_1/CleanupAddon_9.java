				boolean makeInvisible = true;

				for (MUIElement kid : container.getChildren()) {
					if (!kid.isToBeRendered())
						continue;
					if (kid.isVisible()) {
						makeInvisible = false;
						break;
