				break;

			case MODIFY:
				if (sameType(entry.getOldMode()
					entries.add(entry);
				else
					entries.addAll(DiffEntry.breakModify(entry));
				break;

			case COPY:
			case RENAME:
			default:
				entriesToAdd.add(entry);
