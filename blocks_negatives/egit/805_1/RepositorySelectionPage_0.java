				try {
					eventDepth++;
					if (eventDepth != 1)
						return;

					final URIish u = new URIish(uriText.getText());
					safeSet(hostText, u.getHost());
					safeSet(pathText, u.getPath());
					safeSet(userText, u.getUser());
					safeSet(passText, u.getPass());

					if (u.getPort() > 0)
						portText.setText(Integer.toString(u.getPort()));
					else

					if (isFile(u))
						scheme.select(S_FILE);
					else if (isSSH(u))
						scheme.select(S_SSH);
					else {
						for (int i = 0; i < DEFAULT_SCHEMES.length; i++) {
							if (DEFAULT_SCHEMES[i].equals(u.getScheme())) {
								scheme.select(i);
								break;
							}
						}
					}

					updateAuthGroup();
					uri = u;
				} catch (URISyntaxException err) {
					uri = new URIish();
					scheme.select(0);
				} finally {
					eventDepth--;
				}
				checkPage();
