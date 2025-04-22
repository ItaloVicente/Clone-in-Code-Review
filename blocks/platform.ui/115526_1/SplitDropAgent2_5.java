
					if (!isInCursorShell(info, ctf)) {
						return false;
					}

					Rectangle bb = ctf.getClientArea();
					bb = ctf.getDisplay().map(ctf, null, bb);
					if (!bb.contains(info.cursorPos)) {
						return false;
					}

					if (dragElement instanceof MPartStack && stack == dragElement) {
						return false;
					}

					MUIElement deParent = dragElement.getParent();
					if (dragElement instanceof MStackElement && stack == deParent
							&& ms.countRenderableChildren(deParent) == 1) {
						return false;
					}

					return true;
