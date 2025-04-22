			break;
		case MERGED:
			String name;
			if (!isMergedInto(oldHead, src))
				name = mergeStrategy.getName();
			else
			outw.println(MessageFormat.format(CLIText.get().mergeMadeBy, name));
			break;
		case MERGED_NOT_COMMITTED:
			outw.println(CLIText.get().mergeWentWellStoppedBeforeCommitting);
			break;
		case MERGED_SQUASHED:
		case FAST_FORWARD_SQUASHED:
		case MERGED_SQUASHED_NOT_COMMITTED:
			outw.println(CLIText.get().mergedSquashed);
			outw.println(CLIText.get().mergeWentWellStoppedBeforeCommitting);
			break;
		case ABORTED:
			throw die(CLIText.get().ffNotPossibleAborting);
		case NOT_SUPPORTED:
			outw.println(MessageFormat.format(
					CLIText.get().unsupportedOperation, result.toString()));
