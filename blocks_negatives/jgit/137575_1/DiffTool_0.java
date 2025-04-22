					outw.println("M\t" + ent.getNewPath()
							+ " (" + ent.getNewId().name() + ")"
							+ "\t" + ent.getOldPath()
							+ " (" + ent.getOldId().name() + ")");
					outw.println("--- NEW-DATA ---");

					ObjectStream newFileStream = sourcePair.open(Side.NEW, ent)
							.openStream();
					showStream(newFileStream);
					outw.println("--- OLD-DATA ---");
					ObjectStream oldFileStream = sourcePair.open(Side.OLD, ent)
							.openStream();
					showStream(oldFileStream);

					diffToolMgr.compare(ent.getNewPath(),
							ent.getOldPath(), ent.getNewId().name(),
							ent.getOldId().name(), toolName, prompt, gui,
							trustExitCode);
