						try {
							stream = url.openStream();
							InputSource source = new InputSource();
							source.setByteStream(stream);
							source.setURI(url.toString());
							engine.parseStyleSheet(source);
						} catch (IOException e) {
							ThemeEngineManager.logError(e.getMessage(), e);
						} finally {
							if (stream != null) {
								try {
									stream.close();
								} catch (IOException e) {
									ThemeEngineManager.logError(e.getMessage(), e);
