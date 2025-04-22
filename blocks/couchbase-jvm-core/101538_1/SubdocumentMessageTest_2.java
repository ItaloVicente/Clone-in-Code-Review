                "EXIST(sub2): SUBDOC_PATH_NOT_FOUND\n";

        if (useMock()) {
            expected = expected + "GET(sub): SUCCESS = {\"value\":\"subStringValue\",\"array\":[\"array1\",2,true]}\n";
        } else {
            expected = expected + "GET(sub): SUCCESS = {\"value\": \"subStringValue\",\"array\": [\"array1\", 2, true]}\n";
        }

        expected = expected +
            "GET(sub.array[1]): SUCCESS = 2\n" +
            "GET(sub2): SUBDOC_PATH_NOT_FOUND\n";
