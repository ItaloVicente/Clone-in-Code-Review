
        if (saslClient.isComplete() || msg.getStatus() == SASL_AUTH_FAILURE) {
            checkIsAuthed(msg);
            return;
        }

