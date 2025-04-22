				try {
					String version = getTag(scmUrl);
					String host = getServer(scmUrl);
					styledString.append(project);
					if (version != null) {
						styledString.append(' ');
						styledString.append(version,
								StyledString.DECORATIONS_STYLER);
					}
