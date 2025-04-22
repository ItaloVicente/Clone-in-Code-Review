            try {
                decodeAttributes(attributes, serverFirstMessage);
            } catch (Exception ex) {
                throw new SaslException("Could not decode attributes from server message \""
                    + serverFirstMessage + "\"", ex);
            }
