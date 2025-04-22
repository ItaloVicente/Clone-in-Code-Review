				stream = url.openStream();
				cssEngine.parseStyleSheet(stream);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
