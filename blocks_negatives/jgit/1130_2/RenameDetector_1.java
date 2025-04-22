				if (sameType(e.oldMode, dst.newMode)) {
					if (e.changeType == ChangeType.DELETE) {
						e.changeType = ChangeType.RENAME;
						entries.add(exactRename(e, dst));
					} else {
						entries.add(exactCopy(e, dst));
					}
