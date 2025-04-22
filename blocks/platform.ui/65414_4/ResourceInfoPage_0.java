
				Button goToLocationButton = new Button(basicInfoComposite, SWT.PUSH);
				ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(),
						goToLocationButton);
				Bundle bundle = FrameworkUtil.getBundle(getClass());
				URL goToFolderUrl = FileLocator.find(bundle, new Path("icons/full/obj16/goto_input.png"), //$NON-NLS-1$
						null);
				goToLocationButton.setImage(resourceManager.createImage(ImageDescriptor.createFromURL(goToFolderUrl)));
				goToLocationButton.setToolTipText(LOCATION_BUTTON_TOOLTIP);
				goToLocationButton.addSelectionListener(new SelectionAdapter() {

					@SuppressWarnings("restriction")
					@Override
					public void widgetSelected(SelectionEvent e) {
						ECommandService commandService = PlatformUI.getWorkbench().getService(ECommandService.class);
						EHandlerService handlerService = PlatformUI.getWorkbench().getService(EHandlerService.class);

						Command command = commandService.getCommand(ShowInSystemExplorerHandler.ID);
						if (command.isDefined()) {
							ParameterizedCommand parameterizedCommand = commandService
									.createCommand(ShowInSystemExplorerHandler.ID, null);
							handlerService.executeHandler(parameterizedCommand);
						}
					}
				});
