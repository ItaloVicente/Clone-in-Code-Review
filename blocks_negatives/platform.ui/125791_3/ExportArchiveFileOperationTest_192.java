			} else {
				File file = new File(filePath);
				try (InputStream fin = new FileInputStream(file)) {
					try (InputStream in = new GZIPInputStream(fin)) {
						compressed = true;
					} catch (IOException e) {
						compressed = false;
					}
					fileName = file.getName();
				}
			}
		} catch (IOException e) {
			fail(e.getMessage());
		}
