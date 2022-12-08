package uz.raximov.payload;

public record RMSDObject(String mod, String op, String kml) {
    public RMSDObject(String kml) {
        this("trailforks_contribute", "kml_to_wkt", kml);
    }
}
