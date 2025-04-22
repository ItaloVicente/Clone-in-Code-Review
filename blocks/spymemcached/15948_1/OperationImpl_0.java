
package net.spy.memcached.ops;

public enum ErrorCode {

  SUCCESS((byte) 0x00),

  ERR_NOT_FOUND((byte) 0x01),

  ERR_EXISTS((byte) 0x02),

  ERR_2BIG((byte) 0x03),

  ERR_INVAL((byte) 0x04),

  ERR_NOT_STORED((byte) 0x05),

  ERR_DELTA_BADVAL((byte) 0x06),

  ERR_NOT_MY_VBUCKET((byte) 0x07),

  ERR_AUTH_ERROR((byte) 0x20),

  ERR_AUTH_CONTINUE((byte) 0x21),
  ERR_UNKNOWN_COMMAND((byte) 0x81),

  ERR_NO_MEM((byte) 0x82),

  ERR_NOT_SUPPORTED((byte) 0x83),

  ERR_INTERNAL((byte) 0x84),

  ERR_BUSY((byte) 0x85),

  ERR_TEMP_FAIL((byte) 0x86),

  CANCELLED((byte) 0xF1),

  TIMED_OUT((byte) 0xF2),

  EXCEPTION((byte) 0xF3);

  private final byte error;

  ErrorCode(byte err) {
    error = err;
  }

  public static ErrorCode getErrorCode(byte b) {
    if (b == ErrorCode.SUCCESS.error) {
      return ErrorCode.SUCCESS;
    } else if (b == ErrorCode.ERR_NOT_FOUND.error) {
      return ErrorCode.ERR_NOT_FOUND;
    } else if (b == ErrorCode.ERR_EXISTS.error) {
      return ErrorCode.ERR_EXISTS;
    } else if (b == ErrorCode.ERR_2BIG.error) {
      return ErrorCode.ERR_2BIG;
    } else if (b == ErrorCode.ERR_INVAL.error) {
      return ErrorCode.ERR_INVAL;
    } else if (b == ErrorCode.ERR_NOT_STORED.error) {
      return ErrorCode.ERR_NOT_STORED;
    } else if (b == ErrorCode.ERR_DELTA_BADVAL.error) {
      return ErrorCode.ERR_DELTA_BADVAL;
    } else if (b == ErrorCode.ERR_NOT_MY_VBUCKET.error) {
      return ErrorCode.ERR_NOT_MY_VBUCKET;
    } else if (b == ErrorCode.ERR_AUTH_ERROR.error) {
      return ErrorCode.ERR_AUTH_ERROR;
    } else if (b == ErrorCode.ERR_AUTH_CONTINUE.error) {
      return ErrorCode.ERR_AUTH_CONTINUE;
    }  else if (b == ErrorCode.ERR_UNKNOWN_COMMAND.error) {
      return ErrorCode.ERR_UNKNOWN_COMMAND;
    }  else if (b == ErrorCode.ERR_NO_MEM.error) {
      return ErrorCode.ERR_NO_MEM;
    }  else if (b == ErrorCode.ERR_NOT_SUPPORTED.error) {
      return ErrorCode.ERR_NOT_SUPPORTED;
    }  else if (b == ErrorCode.ERR_INTERNAL.error) {
      return ErrorCode.ERR_INTERNAL;
    }  else if (b == ErrorCode.ERR_BUSY.error) {
      return ErrorCode.ERR_BUSY;
    }  else if (b == ErrorCode.ERR_TEMP_FAIL.error) {
      return ErrorCode.ERR_TEMP_FAIL;
    }
    return null;
  }
}
