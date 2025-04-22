        
        if (saslClient.isComplete()) {
            checkIsAuthed(msg);
            return;
        }

