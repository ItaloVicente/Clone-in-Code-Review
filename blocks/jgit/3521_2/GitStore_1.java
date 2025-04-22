      private int sequence_ ;
      public boolean hasSequence() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      public int getSequence() {
        return sequence_;
      }
      public Builder setSequence(int value) {
        bitField0_ |= 0x00000001;
        sequence_ = value;
        onChanged();
        return this;
      }
      public Builder clearSequence() {
        bitField0_ = (bitField0_ & ~0x00000001);
        sequence_ = 0;
        onChanged();
        return this;
      }

