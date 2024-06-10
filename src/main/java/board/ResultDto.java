package board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ResultDto {
    private TbPublicWifiInfoDto TbPublicWifiInfo;

    @AllArgsConstructor
    @Getter
    @Setter
    public class TbPublicWifiInfoDto {
        private RowDto[] row;
        private int list_total_count;

        @Getter
        @Setter
        public class Result {
            private String CODE;
            private String MESSAGE;
        }

        @Getter
        @Setter
        public class RowDto {
            private String X_SWIFI_MGR_NO;
            private String X_SWIFI_WRDOFC;
            private String X_SWIFI_MAIN_NM;
            private String X_SWIFI_ADRES1;
            private String X_SWIFI_ADRES2;
            private String X_SWIFI_INSTL_FLOOR;
            private String X_SWIFI_INSTL_TY;
            private String X_SWIFI_INSTL_MBY;
            private String X_SWIFI_SVC_SE;
            private String X_SWIFI_CMCWR;
            private String X_SWIFI_CNSTC_YEAR;
            private String X_SWIFI_INOUT_DOOR;
            private String X_SWIFI_REMARS3;
            private java.math.BigDecimal LAT;
            private java.math.BigDecimal LNT;
            private String WORK_DTTM;
        }
    }
}
