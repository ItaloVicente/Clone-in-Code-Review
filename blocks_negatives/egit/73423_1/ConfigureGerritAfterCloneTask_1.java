							if (response.startsWith(GERRIT_XSSI_MAGIC_STRING)) {
								return true;
							} else {
								return false;
							}
						case HttpURLConnection.HTTP_NOT_FOUND:
							if (slash > path.length()) {
								return false;
							}
							slash = path.indexOf('/', slash);
							if (slash == -1) {
								slash = path.length();
							}
							tmpPath = path.substring(0, slash);
							slash++;
							break;
						default:
