		if (null == op) {
                    throw die(MessageFormat.format(CLIText.get().unsupportedOperation
                } else switch (op) {
                    final URLConnection c = s3.get(bucket
                    int len = c.getContentLength();
                    try (InputStream in = c.getInputStream()) {
                        outw.flush();
                        final byte[] tmp = new byte[2048];
                        while (len > 0) {
                            final int n = in.read(tmp);
                            if (n < 0)
                                throw new EOFException(MessageFormat.format(
                                        CLIText.get().expectedNumberOfbytes
                                        valueOf(len)));
                            outs.write(tmp
                            len -= n;
                        }
                        outs.flush();
                    }
                    break;
                    for (String k : s3.list(bucket
                        outw.println(k);
                    break;
                    s3.delete(bucket
                    break;
                    try (OutputStream os = s3.beginPut(bucket
                        final byte[] tmp = new byte[2048];
                        int n;
                        while ((n = ins.read(tmp)) > 0)
                            os.write(tmp
                    }
                    break;
                default:
                    throw die(MessageFormat.format(CLIText.get().unsupportedOperation
            }
