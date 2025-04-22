			} else {
				Shell limbo = (Shell) app.getContext().get("limbo");

				Composite curParent = ctrl.getParent();
				ctrl.setParent(limbo);
				curParent.layout(true);
				if (curParent.getShell() != curParent)
					curParent.getShell().layout(new Control[] { curParent }, SWT.DEFER);

				if ((Object) parent instanceof MWindow)
