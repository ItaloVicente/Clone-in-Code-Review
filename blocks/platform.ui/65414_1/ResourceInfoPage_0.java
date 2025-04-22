
				Button goToLocationButton = new Button(basicInfoComposite, SWT.PUSH);
				ResourceManager resourceManager = new LocalResourceManager(JFaceResources.getResources(),
						goToLocationButton);
				Bundle bundle = FrameworkUtil.getBundle(getClass());
				URL goToFolderUrl = FileLocator.find(bundle, new Path("icons/full/obj16/output_folder_attrib.png"), //$NON-NLS-1$
						null);
				goToLocationButton.setImage(resourceManager.createImage(ImageDescriptor.createFromURL(goToFolderUrl)));
				goToLocationButton.setToolTipText(LOCATION_BUTTON_TOOLTIP);
				goToLocationButton.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						File locationFile = new File(locationStr);
						if (locationFile.isDirectory()) {
							Program.launch(locationStr);
						} else {
							Program.launch(locationFile.getParent());
						}
					}
				});
