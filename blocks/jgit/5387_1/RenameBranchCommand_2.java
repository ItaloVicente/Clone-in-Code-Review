							shortOldName
					if (values.length == 0)
						continue;
					String[] existing = repoConfig.getStringList(
							ConfigConstants.CONFIG_BRANCH_SECTION
							name);
					if (existing.length > 0) {
						String[] newValues = new String[values.length
								+ existing.length];
						System.arraycopy(existing
								existing.length);
						System.arraycopy(values
								values.length);
						values = newValues;
