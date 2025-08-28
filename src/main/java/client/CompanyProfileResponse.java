package client;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyProfileResponse {
    @JsonProperty("marketCapitalization")
    public Double marketCapitalization;

    @JsonProperty("shareOutstanding")
    public Double shareOutstanding;

    // getters and setters
    public Double getMarketCapitalization() { return marketCapitalization; }
    public void setMarketCapitalization(Double marketCapitalization) { this.marketCapitalization = marketCapitalization; }

    public Double getShareOutstanding() { return shareOutstanding; }
    public void setShareOutstanding(Double shareOutstanding) { this.shareOutstanding = shareOutstanding; }
}
