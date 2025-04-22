		FILTERNONE = map(Platform.getBundle("org.eclipse.ui.ide").getVersion() //$NON-NLS-1$
				.compareTo(Version.valueOf("3.16.100")) >= 0 ? //$NON-NLS-1$
						"elcl16/filter_none_funnel.png" //$NON-NLS-1$
						: "elcl16/filter_none.png"); //$NON-NLS-1$
		FILTERRESOURCE = map(
				Platform.getBundle("org.eclipse.ui.ide").getVersion() //$NON-NLS-1$
						.compareTo(Version.valueOf("3.16.100")) >= 0 ? //$NON-NLS-1$
								"elcl16/filter_resource_funnel.png" //$NON-NLS-1$
								: "elcl16/filterresource.png"); //$NON-NLS-1$
		FILTERPROJECT = map(
				Platform.getBundle("org.eclipse.ui.ide").getVersion() //$NON-NLS-1$
						.compareTo(Version.valueOf("3.16.100")) >= 0 ? //$NON-NLS-1$
								"elcl16/filter_project_funnel.png" //$NON-NLS-1$
								: "elcl16/filterproject.png"); //$NON-NLS-1$
		FILTERFOLDER = map(Platform.getBundle("org.eclipse.ui.ide").getVersion() //$NON-NLS-1$
				.compareTo(Version.valueOf("3.16.100")) >= 0 ? //$NON-NLS-1$
						"elcl16/filter_folder_funnel.png" //$NON-NLS-1$
						: "elcl16/filterfolder.png"); //$NON-NLS-1$
