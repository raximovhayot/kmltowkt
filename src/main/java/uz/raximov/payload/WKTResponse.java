package uz.raximov.payload;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WKTResponse {
    private boolean status;
    private boolean rmsS;
    private String rmsM;
    private String rmsP;
    private String[] rmsD;

    public String getRmsD() {
        return rmsD[0];
    }

    public WKTResponse(boolean rmsS, String rmsP, String[] rmsD) {
        this.rmsS = rmsS;
        this.rmsP = rmsP;
        this.rmsD = rmsD;
    }
}