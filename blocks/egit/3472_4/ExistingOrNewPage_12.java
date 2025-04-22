			boolean pageComplete = viewer.getCheckedElements().length > 0;
			for (Object checkedElement : viewer.getCheckedElements()) {
				String path = ((ProjectAndRepo) checkedElement).getRepo();
				if (((ProjectAndRepo) checkedElement).getRepo() != null
						&& path.equals("")) { //$NON-NLS-1$
					pageComplete = false;
				}
