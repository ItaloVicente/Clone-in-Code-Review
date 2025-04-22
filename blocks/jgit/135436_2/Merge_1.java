			switch (result.getMergeStatus()) {
			case ALREADY_UP_TO_DATE:
				if (squash)
					outw.print(CLIText.get().nothingToSquash);
				outw.println(CLIText.get().alreadyUpToDate);
				break;
			case FAST_FORWARD:
				ObjectId oldHeadId = oldHead.getObjectId();
				if (oldHeadId != null) {
					String oldId = oldHeadId.abbreviate(7).name();
					String newId = result.getNewHead().abbreviate(7).name();
					outw.println(MessageFormat.format(CLIText.get().updating
							oldId
