				String name;
				if (!isMergedInto(oldHead, src)) {
					name = mergeStrategy.getName();
				} else {
				}
				outw.println(
						MessageFormat.format(CLIText.get().mergeMadeBy, name));
