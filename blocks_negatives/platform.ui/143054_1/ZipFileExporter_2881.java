        if (!useCompression) {
            entry.setMethod(ZipEntry.STORED);
        	InputStream contentStream = contents.getContents(false);
        	int length = 0;
            CRC32 checksumCalculator = new CRC32();
            try {
                int n;
                while ((n = contentStream.read(readBuffer)) > 0) {
                    checksumCalculator.update(readBuffer, 0, n);
                    length += n;
                }
            } finally {
                if (contentStream != null) {
