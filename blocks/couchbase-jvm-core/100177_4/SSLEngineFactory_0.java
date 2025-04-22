            if (!sslContextProtocol.startsWith("TLS")) {
                throw new IllegalArgumentException(
                    "SSLContext Protocol does not start with TLS, this is to prevent "
                        + "insecure protocols (Like SSL*) to be used. Potential candidates "
                        + "are TLS (default), TLSv1, TLSv1.1, TLSv1.2, TLSv1.3 depending on "
                        + "the Java version used.");
            }
            SSLContext ctx = SSLContext.getInstance(sslContextProtocol);
