				MPartStack.class, EModelService.PRESENTATION, new Selector() {
					@Override
					public boolean select(MApplicationElement element) {
						MPartStack stack = (MPartStack) element;

						if (!stack.isVisible() || !(stack.getWidget() instanceof CTabFolder)) {
							return false;
						}

						CTabFolder ctf = (CTabFolder) stack.getWidget();
						if (ctf.isDisposed()) {
							return false;
						}

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
