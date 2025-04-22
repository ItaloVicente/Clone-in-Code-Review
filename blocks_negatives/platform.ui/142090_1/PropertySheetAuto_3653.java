    /**
     * This car serves as a simple porperty source.
     * The only interesting behavior it has is that if
     * one of its properties has a "null" value then
     * it does not include that property in its list
     * of property descriptors.
     */
    private class Car implements IPropertySource {
        private int modelYear = 0;
