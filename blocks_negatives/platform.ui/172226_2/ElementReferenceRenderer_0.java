		} else {
			if (refCtrl != null && !refCtrl.isDisposed()) {
				MPlaceholder currentRef = refElement.getCurSharedRef();
				if (currentRef == ph) {
					for (MPlaceholder aPH : refs) {
						Composite phComp = (Composite) aPH.getWidget();
						if (phComp == null || phComp.isDisposed())
							continue;

						IEclipseContext newParentContext = modelService
								.getContainingContext(aPH);
						List<MContext> allContexts = modelService.findElements(refElement, null, MContext.class);
						for (MContext ctxtElement : allContexts) {
							IEclipseContext theContext = ctxtElement
									.getContext();
							if (theContext != null) {
								if (theContext.getParent() == curContext) {
									if (curContext.getActiveChild() == theContext) {
										theContext.deactivate();
									}
									theContext.setParent(newParentContext);
								}
							}
						}

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
