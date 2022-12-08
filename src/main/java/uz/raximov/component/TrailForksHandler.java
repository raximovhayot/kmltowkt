package uz.raximov.component;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import uz.raximov.payload.RMSDObject;
import uz.raximov.payload.WKTResponse;

public class TrailForksHandler {
    public WKTResponse request(RMSDObject rmsdObject, Gson gson) throws RuntimeException {
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> jsonNode = Unirest.post("https://www.trailforks.com/rms/index.php")
                    .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:102.0) Gecko/20100101 Firefox/102.0")
                    .header("Accept", "application/json, text/javascript, */*; q=0.01")
                    .header("Accept-Language", "en-US,en;q=0.5")
                    .header("Accept-Encoding", "gzip, deflate, br")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("X-Requested-With", "XMLHttpRequest")
                    .header("Origin", "https://www.trailforks.com")
                    .header("Connection", "keep-alive")
                    .header("Referer", "https://www.trailforks.com/tools/kmlwkt/")
                    .header("Cookie", "pbid2=49ad88aa5c2243c98b95153a28cbaf1b; tfs=4; pb2=VNNlrhT-RXCaxmWa%2CRQbwqjYkr; _gcl_au=1.1.1608632210.1668168017; _ga_H3Q0X2JJ93=GS1.1.1668168017.1.1.1668168066.0.0.0; _ga=GA1.1.367723429.1668168017; _gid=GA1.2.399997477.1668168018; ajs_anonymous_id=604e7d96-18ab-47b4-b08d-66d7b4668425; _fbp=fb.1.1668168017747.1741804981; _meta_facebookTag_sync=1668168017747; _meta_metarouter_sessionID=1668168017746; _meta_metarouter_timezone_offset=-300; afUserId=b1c908b3-1d9b-4f8d-8419-ee787317c29d-p; AF_SYNC=1668168019608; __stripe_mid=19b78e5a-5c79-45e9-8455-f1764de22f472350f0; __stripe_sid=62004980-5a7e-4b94-9b5a-19cc150f3f9864eb38")
                    .header("Sec-Fetch-Dest", "empty")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .field("rmsP", "j1")
                    .field("rmsD", gson.toJson(rmsdObject)).asString();
            String body = jsonNode.getBody();
            WKTResponse wktResponse = gson.fromJson(body, WKTResponse.class);
            wktResponse.setStatus(jsonNode.getStatus() == 200);
            return wktResponse;
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}
