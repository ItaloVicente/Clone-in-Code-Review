				item.setText(part.getLabel());
				IWorkbenchWindow iwbw = page.getWorkbenchWindow();
				if (iwbw instanceof WorkbenchWindow) {
					WorkbenchWindow wbw = (WorkbenchWindow) iwbw;
					if (part != null && wbw.getModel().getRenderer() instanceof SWTPartRenderer) {
						SWTPartRenderer r = (SWTPartRenderer) wbw.getModel().getRenderer();
						item.setImage(r.getImage(part));
					}
				}
				item.setData(part);
