			for (String sub : config.getSubsections(key)) {
				for (String sectionItem : config.getNames(key, sub)) {
					String sectionId = key + "." + sub + "." + sectionItem; //$NON-NLS-1$ //$NON-NLS-2$
					PropertyDescriptor desc = new PropertyDescriptor(prefix
							+ sectionId, sectionId);
					desc.setCategory(category);
