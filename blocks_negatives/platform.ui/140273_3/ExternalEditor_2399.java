        if (configurationElement != null) {
            try {
                Bundle bundle = Platform.getBundle(configurationElement
						.getContributor().getName());
                URL entry = bundle.getEntry(descriptor.getFileName());
                if (entry != null) {
                    URL localName = Platform.asLocalURL(entry);
                    File file = new File(localName.getFile());
                    if (file.exists()) {
