public class RequestMessage extends BaseMessage {
  private static final int NUM_VBUCKETS = 1024;
  private static final int FLAGS_FIELD_LENGTH = 4;
  private static final int BACKFILL_DATE_FIELD_LENGTH = 8;
  private static final int VBUCKET_LIST_FIELD_LENGTH = 2;

  private byte[] name;
  private byte[] flags;
  private byte[] backfilldate;
  private byte[] vbucketlist;
  private byte[] value;

  public RequestMessage() {
    flags = new byte[0];
    name = new byte[0];
    backfilldate = new byte[0];
    vbucketlist = new byte[0];
    value = new byte[0];
  }

  public void setFlags(TapFlag f) {
    if (flags.length != FLAGS_FIELD_LENGTH) {
      flags = new byte[FLAGS_FIELD_LENGTH];
    }
    if (!f.hasFlag(getFlags())) {
      long curFlags = Util.fieldToValue(flags, 0, FLAGS_FIELD_LENGTH);
      Util.valueToFieldOffest(flags, 0, FLAGS_FIELD_LENGTH, curFlags
          + f.getFlag());
      encode();
    }
  }

  public void setBackfill(long date) {
    backfilldate = new byte[BACKFILL_DATE_FIELD_LENGTH];
    Util.valueToFieldOffest(backfilldate, 0, BACKFILL_DATE_FIELD_LENGTH, date);
    encode();
  }

  public int getFlags() {
    if (flags.length != FLAGS_FIELD_LENGTH) {
      return 0;
    }
    return (int) Util.fieldToValue(flags, 0, FLAGS_FIELD_LENGTH);
  }

  public void setVbucketlist(int[] vbs) {
    byte[] vblist = new byte[(vbs.length + 1) * VBUCKET_LIST_FIELD_LENGTH];
    for (int i = 0; i < vbs.length + 1; i++) {
      if (i == 0) {
        Util.valueToFieldOffest(vblist, 0, VBUCKET_LIST_FIELD_LENGTH,
            (long) vbs.length);
      } else if (vbs[i - 1] < NUM_VBUCKETS && vbs[i - 1] >= 0) {
        Util.valueToFieldOffest(vblist, (i * VBUCKET_LIST_FIELD_LENGTH),
            VBUCKET_LIST_FIELD_LENGTH, (long) vbs[i - 1]);
      } else {
        getLogger().error("vBucket ignored " + vbs[i - 1]
            + "is not a valid vBucket number");
      }
    }
    vbucketlist = vblist;
    encode();
  }

  public void setName(String s) {
    long len = s.length();
    if (len >= (int) Math.pow(256, (double) (KEY_LENGTH_FIELD_LENGTH))) {
      throw new IllegalArgumentException("Name too big");
    }
    name = s.getBytes();
    setKeylength(len);
    encode();
  }

  private void encode() {
    byte[] buffer = new byte[HEADER_LENGTH + name.length + flags.length
      + vbucketlist.length + backfilldate.length + value.length];

    int totalbody = 0; // Begin recording total body
    int extralength = 0; // Begin recording extra length

    for (int i = 0; i < flags.length; totalbody++, extralength++, i++) {
      buffer[HEADER_LENGTH + totalbody] = flags[i];
    }
    setExtralength(extralength); // Stop recording extra length

    for (int i = 0; i < name.length; totalbody++, i++) {
      buffer[HEADER_LENGTH + totalbody] = name[i];
    }

    if (TapFlag.BACKFILL.hasFlag(getFlags())) {
      for (int i = 0; i < backfilldate.length; totalbody++, i++) {
        buffer[HEADER_LENGTH + totalbody] = backfilldate[i];
      }
    }

    if (TapFlag.LIST_VBUCKETS.hasFlag(getFlags())) {
      for (int i = 0; i < vbucketlist.length; totalbody++, i++) {
        buffer[HEADER_LENGTH + totalbody] = vbucketlist[i];
      }
    }

    for (int i = 0; i < value.length; totalbody++, i++) {
      buffer[HEADER_LENGTH + totalbody] = value[i];
    }
    setTotalbody(totalbody); // Stop recording total body

    for (int i = 0; i < HEADER_LENGTH; i++) {
      buffer[i] = mbytes[i];
    }
    mbytes = buffer;
  }
