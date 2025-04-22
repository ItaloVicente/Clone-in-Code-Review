		} else if (!resource.isVirtual()) {
			Label locationTitle = new Label(basicInfoComposite, SWT.LEFT);
			locationTitle.setText(LOCATION_TITLE);
			gd = new GridData();
			locationTitle.setLayoutData(gd);

			Text locationValue = new Text(basicInfoComposite, SWT.WRAP
					| SWT.READ_ONLY);
			final String locationStr = TextProcessor.process(IDEResourceInfoUtils
					.getLocationText(resource));
			locationValue.setText(locationStr);
			gd = new GridData();
			gd.horizontalAlignment = GridData.FILL;
			locationValue.setLayoutData(gd);
			locationValue.setBackground(locationValue.getDisplay()
					.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));

			Button goToLocationButton = new Button(basicInfoComposite, SWT.PUSH);
			gd = new GridData();
			gd.verticalAlignment = SWT.TOP;
			goToLocationButton.setLayoutData(gd);
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
								.createCommand(ShowInSystemExplorerHandler.ID, Collections.singletonMap(
										ShowInSystemExplorerHandler.RESOURCE_PATH_PARAMETER, locationStr));
						if (handlerService.canExecute(parameterizedCommand)) {
							handlerService.executeHandler(parameterizedCommand);
