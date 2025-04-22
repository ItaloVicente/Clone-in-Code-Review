					read(is);
				} catch (Exception e) {
					throw new SAXException(MessageFormat.format(
							RepoText.get().errorIncludeFile, name), e);
				}
			} else if (filename != null) {
				int index = filename.lastIndexOf('/');
				String path = filename.substring(0, index + 1) + name;
				try (InputStream is = new FileInputStream(path)) {
					read(is);
				} catch (IOException e) {
					throw new SAXException(MessageFormat.format(
							RepoText.get().errorIncludeFile, path), e);
