				for (String msg : msgs) {
					if (msg.indexOf("The proxied handler for") == -1
							&& msg.indexOf("Conflict for \'") == -1
							&& msg.indexOf("Keybinding conflicts occurred")==-1
							&& msg.indexOf("A handler conflict occurred")==-1) {
						fail("Failed with: " + msg);
