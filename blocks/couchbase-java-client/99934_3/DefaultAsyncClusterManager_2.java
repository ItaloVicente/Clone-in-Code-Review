        if (settings.compressionMode() != null) {
            String compressionMode;
            switch (settings.compressionMode()) {
                case OFF: compressionMode = "off"; break;
                case ACTIVE: compressionMode = "active"; break;
                case PASSIVE: compressionMode = "passive"; break;
                default:
                    throw new UnsupportedOperationException("Could not convert compression mode "
                            + settings.compressionMode());
            }
            actual.put("compressionMode", compressionMode);
        }

