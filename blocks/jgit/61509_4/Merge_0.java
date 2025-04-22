			if (oldHeadId != null) {
				String oldId = oldHeadId.abbreviate(7).name();
				String newId = result.getNewHead().abbreviate(7).name();
				outw.println(MessageFormat.format(CLIText.get().updating
						newId));
			}
