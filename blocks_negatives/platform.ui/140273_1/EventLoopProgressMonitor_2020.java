                handler.handleException(e);
                break;
            }

            if (System.currentTimeMillis() - t > T_MAX) {
                break;
            }
        }
    }

    @Override
