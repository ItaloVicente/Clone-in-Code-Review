					try {
						control.setRedraw(false);
						if (children.length != 1
								|| children[0] != ERROR_ELEMENT) {
							viewer.remove(ERROR_ELEMENT);
						}
						viewer.add(parent, children);
					} finally {
						control.setRedraw(true);
