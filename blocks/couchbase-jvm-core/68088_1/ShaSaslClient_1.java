        } catch (InvalidKeyException e) {
            if (password == null || password.isEmpty()) {
                throw new UnsupportedOperationException("This JVM does not support empty HMAC keys (empty passwords). "
                    + "Please set a bucket password or upgrade your JVM.");
            } else {
                throw new RuntimeException("Failed to generate HMAC hash for password", e);
            }
