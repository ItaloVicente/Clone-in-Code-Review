					MMenuElement[] ml = menuModel.getChildren().toArray(
							new MMenuElement[menuModel.getChildren().size()]);
					for (int i = 0; i < ml.length; i++) {

						MMenuElement currentMenuElement = ml[i];
						if (currentMenuElement instanceof MDynamicMenuContribution) {
							Object contribution = ((MDynamicMenuContribution) currentMenuElement)
									.getObject();

							IEclipseContext dynamicMenuContext = EclipseContextFactory
									.create();
							@SuppressWarnings("unchecked")
							ArrayList<MMenuElement> mel = (ArrayList<MMenuElement>) currentMenuElement
									.getTransientData()
									.get(MenuManagerShowProcessor.DYNAMIC_ELEMENT_STORAGE_KEY);
							dynamicMenuContext.set(List.class, mel);
							IEclipseContext parentContext = modelService
									.getContainingContext(currentMenuElement);
							ContextInjectionFactory.invoke(contribution,
									AboutToHide.class, parentContext,
									dynamicMenuContext, null);
							dynamicMenuContext.dispose();
						}
