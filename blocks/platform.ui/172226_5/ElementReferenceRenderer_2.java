
					refElement.setCurSharedRef(aPH);

					refCtrl.setParent(phComp);
					break;
				}
			} else if (currentRef != null) {
				Composite phComp = (Composite) currentRef.getWidget();
				if (phComp == null || phComp.isDisposed()) {
					super.disposeWidget(element);
					return;
				}

				IEclipseContext newParentContext = modelService
						.getContainingContext(currentRef);
				List<MContext> allContexts = modelService.findElements(
						refElement, null, MContext.class);
				for (MContext ctxtElement : allContexts) {
					IEclipseContext theContext = ctxtElement.getContext();
					if (theContext != null
							&& theContext.getParent() == curContext) {
						if (curContext.getActiveChild() == theContext) {
							theContext.deactivate();
						}
						theContext.setParent(newParentContext);
					}
