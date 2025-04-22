        InputStream is = null;
        try {
            is = dsURL.openStream();
            BufferedReader reader = new BufferedReader(
					new InputStreamReader(is, StandardCharsets.UTF_8));
            dialogSettings.load(reader);
        } catch (IOException e) {
        } finally {
            try {
                if (is != null) {
					is.close();
				}
            } catch (IOException e) {
            }
        }
    }
