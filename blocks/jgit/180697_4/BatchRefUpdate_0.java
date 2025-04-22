						switch (cmd.getType()) {
							case DELETE:
								break;
							case UPDATE:
							case UPDATE_NONFASTFORWARD:
								RefUpdate ruu = newUpdate(cmd);
								cmd.setResult(ruu.update(walk));
								break;
							case CREATE:
								cmd.setResult(ru.update(walk));
								break;
