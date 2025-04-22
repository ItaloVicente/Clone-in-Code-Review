            })
            .toBlocking()
            .single();
    }

    @Override
    public Boolean disconnect() {
        return disconnect(environment.disconnectTimeout(), TIMEOUT_UNIT);
