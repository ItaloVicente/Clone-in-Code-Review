  @Test
  public void shouldConvertUpdateWithUpdateForClauseSingleIn() {
    String expected = "UPDATE `bucket1` USE KEYS \"abc123\" SET version.description = \"blabla\" FOR version IN versions" +
            " WHEN version.status = \"ACTIVE\" END RETURNING versions";

    Statement statement = Update
            .update(i("bucket1"))
            .useKeysValues("abc123")
            .set(x("version.description"), s("blabla"), forIn("version", "versions").when(x("version.status").eq(s("ACTIVE"))))
            .returning("versions");

    assertEquals(expected, statement.toString());
  }

  @Test
  public void shouldConvertUpdateWithUpdateForClauseSingleWithin() {
    String expected = "UPDATE `bucket1` USE KEYS \"abc123\" SET version.description = \"blabla\" FOR version WITHIN versions" +
            " WHEN version.status = \"ACTIVE\" END RETURNING versions";

    Statement statement = Update
            .update(i("bucket1"))
            .useKeysValues("abc123")
            .set(x("version.description"), s("blabla"), forWithin("version", "versions").when(x("version.status").eq(s("ACTIVE"))))
            .returning("versions");

    assertEquals(expected, statement.toString());
  }

  @Test
  public void shouldConvertUpdateWithUpdateForClauseBothInAndWithin() {
    String expected = "UPDATE `bucket1` USE KEYS \"abc123\" SET version.description = \"blabla\" FOR version IN versions" +
            ", version2 WITHIN altVersions WHEN version.status = \"ACTIVE\" END RETURNING versions";

    Statement statement = Update
            .update(i("bucket1"))
            .useKeysValues("abc123")
            .set(x("version.description"), s("blabla"), forIn("version", "versions")
                    .within("version2", "altVersions").when(x("version.status").eq(s("ACTIVE"))))
            .returning("versions");

    assertEquals(expected, statement.toString());
  }

  @Test
  public void shouldConvertUpdateWithUpdateForClauseNoCondition() {
    String expected = "UPDATE `bucket1` USE KEYS \"abc123\" SET version.description = \"blabla\" FOR version IN versions" +
            ", version2 WITHIN altVersions END RETURNING versions";

    Statement statement = Update
            .update(i("bucket1"))
            .useKeysValues("abc123")
            .set(x("version.description"), s("blabla"), forIn("version", "versions").within("version2", "altVersions").end())
            .returning("versions");

    assertEquals(expected, statement.toString());
  }

