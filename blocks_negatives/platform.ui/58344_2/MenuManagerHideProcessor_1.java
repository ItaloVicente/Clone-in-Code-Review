			menu.getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					for (Entry<MDynamicMenuContribution, ArrayList<MMenuElement>> entry : toBeHidden.entrySet()) {
						MDynamicMenuContribution currentMenuElement = entry.getKey();
						Object contribution = currentMenuElement.getObject();
						IEclipseContext dynamicMenuContext = EclipseContextFactory.create();
