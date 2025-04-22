					PropertyDescriptor desc;
					if (editable)
						desc = new TextPropertyDescriptor(REPO_ID_PREFIX + sub,
								sub);
					else
						desc = new PropertyDescriptor(REPO_ID_PREFIX + sub, sub);
