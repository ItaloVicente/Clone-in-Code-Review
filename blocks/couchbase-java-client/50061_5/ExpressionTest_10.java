    @Test
    public void shouldAcceptArithmeticOperationsForAllKindOfNumbers() {
        Expression add = x(1).add(x("user.id")).add("user.age").add(1).add(100L)
        .add(2.3).add(4.5f);
        assertEquals("1 + user.id + user.age + 1 + 100 + 2.3 + 4.5", add.toString());

        Expression subtract = x(1).subtract(x("user.id")).subtract("user.age").subtract(1).subtract(100L)
        .subtract(2.3).subtract(4.5f);
        assertEquals("1 - user.id - user.age - 1 - 100 - 2.3 - 4.5", subtract.toString());


        Expression multiply = x(1).multiply(x("user.id")).multiply("user.age").multiply(1).multiply(100L)
        .multiply(2.3).multiply(4.5f);
        assertEquals("1 * user.id * user.age * 1 * 100 * 2.3 * 4.5", multiply.toString());

        Expression divide = x(1).divide(x("user.id")).divide("user.age").divide(1).divide(100L)
        .divide(2.3).divide(4.5f);
        assertEquals("1 / user.id / user.age / 1 / 100 / 2.3 / 4.5", divide.toString());
    }

