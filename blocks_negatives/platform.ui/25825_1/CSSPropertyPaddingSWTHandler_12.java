				if (top != pad.x || right != pad.y || bottom != pad.width
						|| left != pad.height) {
					Method m2 = renderer.getClass().getMethod(
							"setPadding",
							new Class[] { int.class, int.class, int.class,
									int.class });
					m2.invoke(renderer, left, right, top, bottom);
				}
