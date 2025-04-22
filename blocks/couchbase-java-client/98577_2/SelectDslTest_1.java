
    @Test
    public void testSimpleAnsiJoin() {
        Expression where = x("airport.type").eq(s("airport"))
                .and("airport.city").eq(s("San Francisco"))
                .and("airport.country").eq(s("United States"));

        Statement statement = selectDistinct("route.destinationairport")
                .from(i("travel-sample") + " airport")
                .join(i("travel-sample") + " route")
                .on(x("airport.faa = route.sourceairport").and("route.type = " + s("route")))
                .where(where);

        String expected = "SELECT DISTINCT route.destinationairport FROM `travel-sample` airport JOIN " +
                "`travel-sample` route ON airport.faa = route.sourceairport AND route.type = \"route\" " +
                "WHERE airport.type = \"airport\" AND airport.city = \"San Francisco\" AND " +
                "airport.country = \"United States\"";
        assertEquals(expected, statement.toString());
    }

    @Test
    public void testAnsiJoinWithOr() {
        Expression onClause = x("hotel.city").eq("landmark.city")
                .and("hotel.country").eq("landmark.country")
                .and("landmark.type").eq(s("landmark"));

        Expression whereClause = x("hotel.type").eq(s("hotel"))
                .and("hotel.title").like(s("Yosemite%"))
                .and("array_length(hotel.public_likes)").gt(5);

        Statement statement = select(
                "hotel.name hotel_name", "landmark.name landmark_name", "landmark.activity"
        )
            .from(i("travel-sample") + " hotel")
            .join(i("travel-sample") + " landmark")
                .on(onClause)
                .where(whereClause);

        String expected = "SELECT hotel.name hotel_name, landmark.name landmark_name, landmark.activity " +
                "FROM `travel-sample` hotel JOIN `travel-sample` landmark ON hotel.city = landmark.city " +
                "AND hotel.country = landmark.country AND landmark.type = \"landmark\" " +
                "WHERE hotel.type = \"hotel\" AND hotel.title LIKE \"Yosemite%\" AND " +
                "array_length(hotel.public_likes) > 5";

        assertEquals(expected, statement.toString());

    }

