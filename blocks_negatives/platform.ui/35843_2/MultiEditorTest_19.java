				for (int i = 0; i < msgs.length; i++) {
					if (msgs[i].indexOf("The proxied handler for") == -1
							&& msgs[i].indexOf("Conflict for \'") == -1
							&& msgs[i].indexOf("Keybinding conflicts occurred")==-1
							&& msgs[i].indexOf("A handler conflict occurred")==-1) {
						fail("Failed with: " + msgs[i]);
