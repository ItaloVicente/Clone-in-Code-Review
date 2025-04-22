				case COPY:
					break;
				default:
					continue;
				}
				String mergedFilePath = ent.getNewPath();
					mergedFilePath = ent.getOldPath();
				}
				FileElement local = new FileElement(ent.getOldPath()
						ent.getOldId().name()
						getObjectStream(sourcePair
				FileElement remote = new FileElement(ent.getNewPath()
						ent.getNewId().name()
						newTreeTemp
								? getObjectStream(sourcePair
								: null);
				try {
					diffToolMgr.compare(db
							toolName
							gui
				} catch (DiffToolException e) {
					outw.println(e.getMessage());
					throw die("external diff died
							+ mergedFilePath
