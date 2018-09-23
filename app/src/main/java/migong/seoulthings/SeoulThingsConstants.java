package migong.seoulthings;

public final class SeoulThingsConstants {

  public static final int SPLASH_TIME_IN_MILLIS = 500;

  public static final int PASSWORD_MIN_LENGTH = 4; // TODO(@gihwan): 8로 설정. 현재 테스트용
  public static final int PASSWORD_MAX_LENGTH = 24;

  public static final String SEOULTHINGS_SERVER_BASE_URL = "http://172.30.1.11:8080";

  private SeoulThingsConstants() {
    // nothing
  }
}
