                "\"sub\":{";

        if (useMock()) {
            expected = expected + "\"array\":[\"unique\",\"first\",\"array1\",\"inserted\",2,true,\"last\"]";
        } else {
            expected = expected + "\"array\":[\"first\",\"array1\",\"inserted\",2,true,\"last\",\"unique\"]";
        }

        expected = expected + ",\"value2\":\"mutated\"" +
