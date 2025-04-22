					}
				} else if (cmd.getMessage() != null) {
					r.append(cmd.getMessage());
				} else {
					switch (cmd.getResult()) {
					case NOT_ATTEMPTED:
						break;
