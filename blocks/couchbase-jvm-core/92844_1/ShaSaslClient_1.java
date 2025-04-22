            try {
                decodeAttributes(attributes, serverFinalMessage);
            } catch (Exception ex) {
                throw new SaslException("Could not decode attributes from server message \""
                    + serverFirstMessage + "\"", ex);
            }
