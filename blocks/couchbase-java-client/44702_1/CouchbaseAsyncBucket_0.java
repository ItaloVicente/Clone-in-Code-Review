                    if (getResponse.status() == ResponseStatus.SUCCESS) {
                        return true;
                    } else {
                        if (getResponse.content() != null) {
                            getResponse.content().release();
                        }
                        return false;
                    }
