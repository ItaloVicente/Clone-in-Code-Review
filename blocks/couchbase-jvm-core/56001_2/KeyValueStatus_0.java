        if (code == SUCCESS.code) {
            return SUCCESS;
        } else if (code == ERR_NOT_FOUND.code) {
            return ERR_NOT_FOUND;
        } else if (code == ERR_EXISTS.code) {
            return ERR_EXISTS;
        } else if (code == ERR_NOT_MY_VBUCKET.code) {
            return ERR_NOT_MY_VBUCKET;
        }

