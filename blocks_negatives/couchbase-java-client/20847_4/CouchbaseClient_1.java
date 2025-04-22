          public void gotData(String key, long retCas,
                  ObserveResponse or) {
            ora[0] = or;
            if (((or == ObserveResponse.FOUND_PERSISTED)
                    || (or == ObserveResponse.FOUND_NOT_PERSISTED))
                    && cas != 0
                    && retCas != cas) {
              ora[0] = ObserveResponse.MODIFIED;
