
			boolean editable = true;

			for (String key : configuredKeys) {


				for (String sub : getSubSections(effectiveConfig, key)) {
					PropertyDescriptor desc;
					if (editable)
						desc = new TextPropertyDescriptor(REPO_ID_PREFIX + sub,
								sub);
					else
						desc = new PropertyDescriptor(REPO_ID_PREFIX + sub, sub);
					desc.setCategory(categoryString);
