		noDefaultAndApplyButton();
		Composite panel = createComposite(parent, 2);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IReadmeConstants.PROPERTY_PAGE2_CONTEXT);

		int page = getPageIndex();
		switch (page) {
		case 1:
			createPageOne(panel);
			break;
		case 2:
			createPageTwo(panel);
			break;
		default:
		}
		return new Canvas(panel, 0);
	}

	protected Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	protected void createPageOne(Composite panel) {
		Label l = createLabel(panel, MessageUtil.getString("Additional_Readme_properties_not_available.")); //$NON-NLS-1$
		GridData gd = (GridData) l.getLayoutData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		l = createLabel(panel,
				MessageUtil.getString("This_illustrates_a_property_page_that_is_dynamically_determined")); //$NON-NLS-1$
		gd = (GridData) l.getLayoutData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		l = createLabel(panel, MessageUtil.getString("not_to_be_available_based_on_the_state_of_the_object.")); //$NON-NLS-1$
		gd = (GridData) l.getLayoutData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
	}

	protected void createPageTwo(Composite panel) {
		Label l = createLabel(panel, MessageUtil.getString("The_size_of_the_Readme_file_is_at_least_256_bytes.")); //$NON-NLS-1$
		GridData gd = (GridData) l.getLayoutData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		l = createLabel(panel,
				MessageUtil.getString("Had_it_been_less_than_256_bytes_this_page_would_be_a_placeholder_page.")); //$NON-NLS-1$
		gd = (GridData) l.getLayoutData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		l = createLabel(panel, MessageUtil.getString("Additional_information")); //$NON-NLS-1$
		gd = (GridData) l.getLayoutData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		l = createLabel(panel,
				MessageUtil.getString("This_illustrates_a_property_page_that_is_dynamically_determined")); //$NON-NLS-1$
		gd = (GridData) l.getLayoutData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
		l = createLabel(panel, MessageUtil.getString("to_be_available_based_on_the_state_of_the_object.")); //$NON-NLS-1$
		gd = (GridData) l.getLayoutData();
		gd.horizontalSpan = 2;
		gd.grabExcessHorizontalSpace = true;
	}

	protected int getPageIndex() {
		IResource resource = (IResource) getElement();

		if (resource.getType() == IResource.FILE) {
			int length = 0;
			IFile file = (IFile) resource;
			try (InputStream contentStream = file.getContents(); Reader in = new InputStreamReader(contentStream);) {

				int chunkSize = contentStream.available();
				StringBuilder buffer = new StringBuilder(chunkSize);
				char[] readBuffer = new char[chunkSize];
				int n = in.read(readBuffer);

				while (n > 0) {
					buffer.append(readBuffer);
					n = in.read(readBuffer);
				}

				contentStream.close();
				length = buffer.length();
			} catch (CoreException e) {
				length = 0;
			} catch (IOException e) {
			}

			if (length < 256)
				return 1;
			return 2;
		}

		return 0;
	}

	@Override
