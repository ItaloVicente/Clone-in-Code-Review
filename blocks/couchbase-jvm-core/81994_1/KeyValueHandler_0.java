            if (errorCode.attributes().contains(ITEM_LOCKED)) {
                errorCode.attributes().remove(RETRY_NOW);
                errorCode.attributes().remove(RETRY_LATER);
                errorCode.attributes().remove(AUTO_RETRY);
                status = ResponseStatus.LOCKED;
            }

