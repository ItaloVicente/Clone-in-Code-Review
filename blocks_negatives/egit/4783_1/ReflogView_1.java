			@Override
			public String getToolTipText(Object element) {
				final ReflogEntry entry = (ReflogEntry) element;
				return entry.getNewId().name();
			}

