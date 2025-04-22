            byte observed = ObserveResponse.ObserveStatus.UNKNOWN.value();
            long observedCas = 0;
            if (status.isSuccess()) {
                short keyLength = content.getShort(2);
                observed = content.getByte(keyLength + 4);
                observedCas = content.getLong(keyLength + 5);
            }
