	/**
	 * A wizard page to choose the target location
	 */
	public class LocationPage extends WizardPage {


		private Button cpRadio;

		private Button fsRadio;

		private Text fsPathText;

		private Button fsBrowseButton;

		private boolean pageValid;

		/**
		 * @param pageName
		 * @param title
		 * @param titleImage
		 */
		protected LocationPage(String pageName, String title,
				ImageDescriptor titleImage) {
			super(pageName, title, titleImage);
		}

		public void createControl(Composite parent) {
			final Composite composite = new Composite(parent, SWT.NULL);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 3;
			composite.setLayout(gridLayout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			initializeDialogUnits(composite);

			GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			gd.horizontalSpan = 3;
			cpRadio = new Button(composite, SWT.RADIO);
			cpRadio.setText(UIText.GitCreatePatchWizard_Clipboard);
			cpRadio.setLayoutData(gd);
			cpRadio.setSelection(true);

			fsRadio = new Button(composite, SWT.RADIO);
			fsRadio.setText(UIText.GitCreatePatchWizard_File);
			fsRadio.setSelection(false);

			fsPathText = new Text(composite, SWT.BORDER);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			fsPathText.setLayoutData(gd);
			fsPathText.setText(createFileName());
			fsPathText.setEnabled(false);

			fsBrowseButton = new Button(composite, SWT.PUSH);
			fsBrowseButton.setText(UIText.GitCreatePatchWizard_Browse);
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			int widthHint = convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH);
			Point minSize = fsBrowseButton.computeSize(SWT.DEFAULT,
					SWT.DEFAULT, true);
			data.widthHint = Math.max(widthHint, minSize.x);
			fsBrowseButton.setLayoutData(data);
			fsBrowseButton.setEnabled(false);

			cpRadio.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					if (((Button) event.widget).getSelection()) {
						fsPathText.setEnabled(false);
						fsBrowseButton.setEnabled(false);
						validatePage();
					}
				}
			});

			fsRadio.addListener(SWT.Selection, new Listener() {

				public void handleEvent(Event event) {
					if (((Button) event.widget).getSelection()) {
						fsPathText.setEnabled(true);
						fsBrowseButton.setEnabled(true);
						fsPathText.setFocus();
						validatePage();
					}
				}
			});

			fsPathText.addModifyListener(new ModifyListener() {

				public void modifyText(ModifyEvent e) {
					if (validatePage()) {
						IPath filePath= Path.fromOSString(fsPathText.getText()).removeLastSegments(1);
						getDialogSettings().put(PATH_KEY, filePath.toPortableString());
					}
				}
			});

			fsBrowseButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					final FileDialog dialog = new FileDialog(getShell(),
							SWT.PRIMARY_MODAL | SWT.SAVE);
					if (pageValid) {
						final File file = new File(fsPathText.getText());
						dialog.setFilterPath(file.getParent());
						dialog.setFileName(file.getName());
					} else {
					}
					final String path = dialog.open();
					if (path != null)
						fsPathText.setText(new Path(path).toOSString());
					validatePage();
				}
			});

			Dialog.applyDialogFont(composite);
			setControl(composite);

		}

		private String createFileName() {
			String suggestedFileName = commit != null ? CreatePatchOperation
					.suggestFileName(commit) : db.getWorkTree().getName()
			String path = getDialogSettings().get(PATH_KEY);
			if (path != null)
				return Path.fromPortableString(path).append(suggestedFileName).toOSString();

			return (new File(System.getProperty("user.dir", ""), suggestedFileName)).getPath(); //$NON-NLS-1$ //$NON-NLS-2$
		}
