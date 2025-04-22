						commands2.add(cmd);
						break;
					case DELETE:
						RefUpdate rud = newUpdate(cmd);
						monitor.update(1);
						cmd.setResult(rud.delete(walk));
