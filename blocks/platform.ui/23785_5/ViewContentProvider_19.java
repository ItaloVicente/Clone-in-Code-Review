				List<String> tags = descriptor.getTags();
				String category = null;
				boolean isView = false;
				for (String tag : tags) {
					if (tag.equals("View")) //$NON-NLS-1$
						isView = true;
					else if (tag.startsWith(CATEGORY_TAG)) {
						category = tag.substring(CATEGORY_TAG_LENGTH);
