    return Observable.from(objects);
  }

  static class MarkersProcessor implements ByteBufProcessor {
    private static final byte open = '{';
    private static final byte close = '}';

    private final List<Integer> markers = new ArrayList<Integer>();

    private int depth;
    private int counter;
