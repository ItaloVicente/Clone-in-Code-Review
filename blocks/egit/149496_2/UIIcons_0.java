		if (Platform.getBundle("org.eclipse.ui.ide").getVersion() //$NON-NLS-1$
				.compareTo(Version.valueOf("3.16.100")) >= 0) { //$NON-NLS-1$
			ELCL16_FILTER = map("elcl16/filter_ps_funnel.png"); //$NON-NLS-1$
			FILTERNONE = map("elcl16/filter_none_funnel.png"); //$NON-NLS-1$
			FILTERRESOURCE = map("elcl16/filter_resource_funnel.png"); //$NON-NLS-1$
			FILTERPROJECT = map("elcl16/filter_project_funnel.png"); //$NON-NLS-1$
			FILTERFOLDER = map("elcl16/filter_folder_funnel.png"); //$NON-NLS-1$
		} else {
			ELCL16_FILTER = map("elcl16/filter_ps.png"); //$NON-NLS-1$
			FILTERNONE = map("elcl16/filter_none.png"); //$NON-NLS-1$
			FILTERRESOURCE = map("elcl16/filterresource.png"); //$NON-NLS-1$
			FILTERPROJECT = map("elcl16/filterproject.png"); //$NON-NLS-1$
			FILTERFOLDER = map("elcl16/filterfolder.png"); //$NON-NLS-1$
		}
