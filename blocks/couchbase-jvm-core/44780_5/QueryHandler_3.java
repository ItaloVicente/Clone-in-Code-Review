        if (queryParsingState == QUERY_STATE_WARNING) {
            parseQueryError(); //warning are treated the same as errors -> sent to errorObservable
        }

        if (queryParsingState == QUERY_STATE_STATUS) {
            parseQueryStatus();
        }

        if (queryParsingState == QUERY_STATE_INFO) {
            parseQueryInfo(lastChunk);
