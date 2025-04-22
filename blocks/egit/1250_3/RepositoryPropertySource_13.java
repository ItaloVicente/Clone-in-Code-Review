			config = userHomeConfig;
			break;
		}
		default:
			return new IPropertyDescriptor[0];
		}
		for (String key : config.getSections()) {
			for (String sectionItem : config.getNames(key)) {
				String sectionId = key + "." + sectionItem; //$NON-NLS-1$
				PropertyDescriptor desc = new PropertyDescriptor(prefix
						+ sectionId, sectionId);
				desc.setCategory(category);
				resultList.add(desc);
