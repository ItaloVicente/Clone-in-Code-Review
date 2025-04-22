    /**
     * Please don't add or remove from this set -- we rely on the exact insertion order
     * to get full coverage from the removal tests.
     */
    private String[] se = new String[] {
            "v00 aaaaaa",
            "v01 apple",
            "v02 booger",
            "v03 car",
            "v04 dog",
            "v05 elephant",
            "v06 fox",
            "v07 goose",
            "v08 hippie",
            "v09 iguana",
            "v10 junk",
            "v11 karma",
            "v12 lemon",
            "v13 mongoose",
            "v14 noodle",
            "v15 opal",
            "v16 pumpkin",
            "v17 quirks",
            "v18 resteraunt",
            "v19 soap",
            "v20 timmy",
            "v21 ugly",
            "v22 virus",
            "v23 wigwam",
            "v24 xerxes",
            "v25 yellow",
            "v26 zero"
    };

    /**
     * Please don't mess with the insertion order here or add or remove from this set --
     * we rely on the exact order in order to get full coverage for the removal tests.
     */
    private String[] elements = new String[] {
            se[19],
            se[7],
            se[6],
            se[1],
            se[20],
            se[8],
            se[0],
            se[23],
            se[17],
            se[18],
            se[24],
            se[25],
            se[10],
            se[5],
            se[15],
            se[16],
            se[21],
            se[26],
            se[22],
            se[3],
            se[9],
            se[4],
            se[11],
            se[12],
            se[13],
            se[14],
            se[2]
        };


    public static void printArray(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("[" + i + "] = " + array[i]);
        }
    }

    @Override
